package com.example.momobe.common.log2.entity;

import lombok.*;
import org.joda.time.base.BaseDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLog2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String domain;
    private String apiURI;
    private String httpMethod;
    private String className;
    private String method;
    private String exceptionName;
    private String errorMessage;
    private LocalDateTime createdAt;

}
