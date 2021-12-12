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
import com.web.liner.request.ReqApplyService;
import com.web.liner.request.ReqApplyWorker;
import com.web.liner.request.ReqCommon;
import com.web.liner.service.MbService;
import com.web.liner.util.Utils;
import com.web.liner.vo.AccountTb;
import com.web.liner.vo.BrandTb;
import com.web.liner.vo.OrderTb;
import com.web.liner.vo.PlaceTb;
import com.web.liner.vo.WorkerTb;

@Controller
@ResponseBody
@RequestMapping("/v1/mb")
public class MobileController {

	private final Logger logger = LoggerFactory.getLogger(MobileController.class);
	private static List<BrandTb> brands;
	
	@Autowired
	MbService mbService;
		
	@Autowired
	ObjectMapper objectMapper;
	
	@RequestMapping(method = RequestMethod.POST, path = "/search/place")
	public Map<String, Object> searchPlaceAndBarnd(@RequestBody ReqCommon param) throws Exception { // 장소&브랜드 조회하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		brands = mbService.searchPlaceAndBarnd();
		res.put(LCons.BRANDS, brands);
		return Utils.resSet(res, param.getCmd());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/apply/line/service")
	public Map<String, Object> applyLineService(@RequestBody ReqApplyService param) throws Exception { // 서비스 구매 신청하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		OrderTb orderTb = mbService.applyLineService(param.getOrder()); // 구매 신청 order 저장
		res.put(LCons.ORDER_CODE, orderTb.getOrderCode()); // res orderCode setting
		return Utils.resSet(res, param.getCmd());
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/check/line/service/{orderCode}")
	public Map<String, Object> checkLineService(@PathVariable("orderCode") String orderCode) throws Exception  { // 주문 예약 확인하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		res.put(LCons.ORDER, mbService.checkLineService(orderCode)); // 주문 예약 확인(orderCode)
		return res;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/apply/line/worker")
	public Map<String, Object> applyLineWorker(@RequestBody ReqApplyWorker param) throws Exception { // 알바 신청하기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		String authCode = mbService.applyLineWorker(param.getWorker(), param.getBankInfo()); // 알바 정보 저장 서비스
		res.put(LCons.AUTH_CODE, authCode); // authCode
		return Utils.resSet(res, param.getCmd());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/search/bank")
	public Map<String, Object> searchBank(@RequestBody ReqCommon param) { // 은행 정보 찾기
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		res.put(LCons.BANKS, mbService.searchBank()); // 은행 정보 찾기 서비스
		return Utils.resSet(res, param.getCmd());
	}

}