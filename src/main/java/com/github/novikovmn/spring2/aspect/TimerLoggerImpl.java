package com.github.novikovmn.spring2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerLoggerImpl {
    @Around("@annotation(logger)")
    protected Object loggerAround(ProceedingJoinPoint point, TimerLogger logger) throws Throwable {
        long timeBefore = System.currentTimeMillis();
        point.proceed();
        long timeAfter = System.currentTimeMillis();
        long executionTime = timeAfter - timeBefore;
        System.out.printf("[Время выполения метода]: %d мс.", executionTime);
        System.out.println();
        return point;
    }
}
