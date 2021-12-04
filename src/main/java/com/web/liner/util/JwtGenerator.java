package com.web.liner.util;

import java.awt.RenderingHints.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.liner.constants.ErrorConstants;
import com.web.liner.controller.MbV1Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtGenerator {

	private final Logger logger = LoggerFactory.getLogger(JwtGenerator.class);

	String apikey = "jwtsigntutorialasdfasdfasdfasdfasdf";

    /**
     * 토큰생성
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMills
     * @return
     */
    public String createJWT(String id, String issuer, String subject, long ttlMills) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 표준 클레임 셋팅
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256, apikey.getBytes());

        // 토큰 만료 시간 셋팅
        if(ttlMills >= 0){
            long expMillis = nowMillis + ttlMills;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // 토큰 생성
        return builder.compact();
    }

    /**
     * 토크인 만료 인증
     * 
     * @param jwt
     * @return
     * @throws LineException 
     * @throws Exception
     */
    public void verifyJWT(String jwt) throws LineException {    	
    	try {
    		Jwts.parser()
			.setSigningKey(apikey.getBytes("UTF-8"))
			.parseClaimsJws(jwt)
			.getBody();
    	} catch (Exception e) {
    		throw new LineException(ErrorConstants.JWT_VERIFY_ERROR, "jwt token verify fail error");
    	}

    }
}
