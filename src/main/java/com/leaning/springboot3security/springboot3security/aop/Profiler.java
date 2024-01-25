package com.leaning.springboot3security.springboot3security.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class Profiler {

    @Around("@annotation(Profile)")
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("EXECUTION STARTS FOR {}", joinPoint.getSignature());
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("EXECUTION of {} TAKEN {}ms", joinPoint.getSignature(), stopWatch.getTotalTimeMillis());
        return proceed;
    }
}
