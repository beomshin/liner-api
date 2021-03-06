package com.web.liner.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.web.liner.constants.ErrorConstants;
import com.web.liner.constants.LCons;
import com.web.liner.controller.AdminController;
import com.web.liner.util.JwtGenerator;
import com.web.liner.util.LineException;
import com.web.liner.util.Utils;

@Aspect
@Component
public class AdminAspect {

	private final Logger logger = LoggerFactory.getLogger(AdminAspect.class);
	
	@Pointcut("execution(public * com.web.liner.controller.AdminController.*(..))") 
	private void adminV1Target() { }
	
	@SuppressWarnings("unchecked")
	@Around("adminV1Target()") public Object controllerLog(ProceedingJoinPoint pjp) {
		
		Map<String, Object> res = new HashMap<String, Object>();
		HttpServletRequest request = null;
		long start = System.currentTimeMillis();

    	try {
    		
    		request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    		logger.info("======> 시작 [{}] 메소드", pjp.getSignature().getName());
    		logger.info("======> URL : [{}]", request.getRequestURL());
    		logger.info("======> 요청 데이터 : [{}]", pjp.getArgs());
    		String token = request.getHeader("Authorization"); // 헤더 인증 토큰 조회
    		new JwtGenerator().verifyJWT(token); // 토큰 인증 처리
    
    		res = (Map<String, Object>) pjp.proceed();
    		res.put(LCons.RESULT_CODE, 0);
    		
    		
    	} catch (LineException e) {
    		res.put(LCons.RESULT_CODE, e.getCode());
    		res.put(LCons.RESULT_MSG, e.getMsg());
    	} catch(Throwable e) {
    		res.put(LCons.RESULT_CODE, ErrorConstants.SYSTEM_ERROR);
    		res.put(LCons.RESULT_MSG, e.getMessage());
    	}finally {
    		request = null;
    		long end = System.currentTimeMillis();
    		logger.info("======> 종료 [{}] 메소드 (수행 시간 [{}])", pjp.getSignature().getName(), end - start);
    	}
    	
    	return res;
	}

	
}
