package com.example.momobe.common.log2.aop;

import com.example.momobe.common.log2.entity.ExceptionLog2;
import com.example.momobe.common.log2.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogAspect {
    private final LogRepository logRepository;

    @AfterThrowing(pointcut = "execution(* com.example.momobe.*.application.*.*(..))", throwing = "ex")
    public void loggingException(JoinPoint joinPoint, Throwable ex) throws Throwable {
        HttpServletRequest request = getRequest();
        String apiURI;
        String httpMethod;
        String domain;

        if(request==null){
            apiURI = null;
            httpMethod = null;
            domain = null;
        }else {
            apiURI = request.getRequestURI();
            httpMethod = request.getMethod();
            domain = request.getServerName();
        }

        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String exceptionName = ex.getClass().getName();
        String errorMessage = ex.getMessage();

        ExceptionLog2 exceptionLog = ExceptionLog2.builder()
                .domain(domain)
                .apiURI(apiURI)
                .httpMethod(httpMethod)
                .className(className)
                .exceptionName(exceptionName)
                .errorMessage(errorMessage)
                .method(method)
                .createdAt(LocalDateTime.now())
                .build();
        logRepository.save(exceptionLog);
        log.error(exceptionName+": "+errorMessage);
    }

    public HttpServletRequest getRequest(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request;
        }catch (IllegalStateException e){
            return null;
        }
    }
}

