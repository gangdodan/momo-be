package com.example.momobe.common.log2.repository;

import com.example.momobe.common.log2.entity.ExceptionLog2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<ExceptionLog2,Long> {

}
