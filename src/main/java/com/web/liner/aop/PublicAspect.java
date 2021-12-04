package com.web.liner.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PublicAspect {

	private final Logger logger = LoggerFactory.getLogger(PublicAspect.class);

	@Pointcut("execution(public * com.web.liner.controller..*(..))") 
	private void publicTarget() { }
	
	@AfterThrowing(value = "publicTarget()", throwing = "e")
    public void writeFailLog(JoinPoint joinPoint, Throwable e) {
    	logger.error("===========> 에러 발생 : ", e);
    }
    
    @AfterReturning(value = "publicTarget()", returning="result")
    public void writeAfterReturn(JoinPoint joinPoint, Object result) {
    	logger.info("======> 응답 데이터 : {}", result.toString());
    }

}
