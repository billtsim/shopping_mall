package com.a88.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class timeAspect {

    @Around("execution(* com.a88.service.*.*(..))")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        // 1. record start time
        long begin = System.currentTimeMillis();
        //2. use 原始方法運行
        Object result = pjp.proceed();
        //3. record end time
        long end = System.currentTimeMillis();

        log.info(pjp.getSignature() + " method used time: {}ms", (end - begin));

        return result;
    }
}
