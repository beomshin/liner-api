package com.web.liner.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.liner.constants.LCons;
import com.web.liner.service.MbService;
import com.web.liner.util.Utils;
import com.web.liner.vo.AccountTb;
import com.web.liner.vo.OrderTb;
import com.web.liner.vo.PlaceTb;
import com.web.liner.vo.WorkerTb;

@Controller
@ResponseBody
@RequestMapping("/v1/mb")
public class MbV1Controller {

	private final Logger logger = LoggerFactory.getLogger(MbV1Controller.class);
	private static List<PlaceTb> places;
	
	@Autowired
	MbService mbService;
		
	@Autowired
	ObjectMapper objectMapper;
	
	@RequestMapping(method = RequestMethod.POST, path = "/search/place")
	public Map<String, Object> searchPlaceAndBarnd(@RequestBody Map<String, Object> param) throws Exception { // 장소&브랜드 조회하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		if (places == null) {
			places = mbService.searchPlaceAndBarnd();
		}
		res.put(LCons.PLACES, places);
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/apply/line/service")
	public Map<String, Object> applyLineService(@RequestBody Map<String, Object> param) throws Exception { // 서비스 구매 신청하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		OrderTb order = objectMapper.convertValue(param.get(LCons.ORDER), OrderTb.class); // 파라미터 order
		OrderTb orderTb = mbService.applyLineService(order); // 구매 신청 order 저장
		res.put(LCons.ORDER_CODE, orderTb.getOrderCode()); // res orderCode setting
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/check/line/service/{orderCode}")
	public Map<String, Object> checkLineService(@PathVariable("orderCode") String orderCode) throws Exception  { // 주문 예약 확인하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		res.put(LCons.ORDER, mbService.checkLineService(orderCode)); // 주문 예약 확인(orderCode)
		return res;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/apply/line/worker")
	public Map<String, Object> applyLineWorker(@RequestBody Map<String, Object> param) throws Exception { // 알바 신청하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		WorkerTb worker = objectMapper.convertValue(param.get(LCons.WORKER), WorkerTb.class); // 파라미터 worker
		AccountTb account = objectMapper.convertValue(param.get(LCons.ACCOUNT), AccountTb.class); // 파라미터 account
		String authCode = mbService.applyLineWorker(worker, account); // 알바 정보 저장 서비스
		res.put(LCons.AUTH_CODE, authCode); // authCode
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/search/bank")
	public Map<String, Object> searchBank(@RequestBody Map<String, Object> param) { // 은행 정보 찾기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		res.put(LCons.BANKS, mbService.searchBank()); // 은행 정보 찾기 서비스
		return Utils.resSet(res, param);
	}

}
