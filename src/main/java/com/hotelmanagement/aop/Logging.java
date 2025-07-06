package com.hotelmanagement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

//    @Before("execution(* com.hotelmanagement..*(..)) && !within(com.hotelmanagement.security..*)")
//    public void beforeExecution(JoinPoint jp) {
//        LOGGER.info("Calling the method : {}", jp.getSignature().getName());
//    }
//
//    @After("execution(* com.hotelmanagement..*(..)) && !within(com.hotelmanagement.security..*)")
//    public void afterExecution(JoinPoint jp) {
//        LOGGER.info("Executed the method : {}", jp.getSignature().getName());
//    }

    @Around("execution(* com.hotelmanagement..*(..)) && !within(com.hotelmanagement.security..*)")
    public Object beforeAfterLogging(ProceedingJoinPoint pjp) throws Throwable {

        String methodName = pjp.getSignature().toShortString();
        Object[] args = pjp.getArgs();

        LOGGER.info("Calling the method : {} {}", methodName, Arrays.toString(args));
        Object object = pjp.proceed();

        methodName = pjp.getSignature().toShortString();
        args = pjp.getArgs();

        LOGGER.info("Executed the method : {} {}", methodName, Arrays.toString(args));
        return object;
    }

    @AfterThrowing(pointcut = "execution(* com.hotelmanagement..*(..)) && !within(com.hotelmanagement.security..*)",
                    throwing = "exception")
    public void errorLogging(JoinPoint jp, Throwable exception) {
        String methodName = jp.getSignature().toShortString();
        Object[] args = jp.getArgs();
        LOGGER.info("Error occurred in the method : {} \n{} \n{}", methodName, exception, exception.getMessage());
    }

}
