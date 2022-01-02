package com.web.liner.util;

import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Utils {

	public static String createOrderCode() { // orderCoe 생성
		return ("" + System.nanoTime()).substring(0,5);
	}
	
	public static String replaceCMD(String CMD) { // cmd req -> res 변경
		return CMD.replace("req", "res");
	}
	
	public static Map<String, Object> resSet(Map<String, Object> res, String cmd) { // response 공통 세팅
		res.put("cmd", Utils.replaceCMD(cmd));
		return res;
	}
	
	public static String createAuthCode() { // AuthCode 생성
		return ("" + UUID.randomUUID()).replaceAll("-", "").substring(2,14);
	}
}
