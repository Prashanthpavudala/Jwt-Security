package com.leaning.springboot3security.springboot3security.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogErrorAspect {

    @AfterThrowing(pointcut = "@annotation(LogError)", throwing = "throwable")
    public void logError(JoinPoint joinPoint, Throwable throwable) {
        log.error("*** Exception occurred at : {} is ", joinPoint, throwable);
    }
}
