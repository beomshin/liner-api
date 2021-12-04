package com.web.liner.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.liner.dao.OrderTbRepository;
import com.web.liner.service.AdminService;
import com.web.liner.util.Utils;

@Controller
@ResponseBody
@RequestMapping("/v1/admin")
public class AdminV1Controller {

	private final Logger logger = LoggerFactory.getLogger(AdminV1Controller.class);

	@Autowired
	OrderTbRepository orderTbRepository;
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/order/list")
	public Map<String, Object> orderList(@RequestBody Map<String, Object> param) throws Exception { // 주문 내역 조회 컨트롤러
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.orderList(res, param); // 주문내역 조회
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/order/worker/list")
	public Map<String, Object> orderWorkerList(@RequestBody Map<String, Object> param) throws Exception { // 알바 리스트 조회 
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.orderWorkerList(res, param); // 알바 리스트 조회 ( 이름, 핸드폰 필터링)
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/order/worker/assign")
	public Map<String, Object>  orderWorkerAssign(@RequestBody Map<String, Object> param) { // 알바 배정
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.orderWorkerAssign(param); // 주문내역 조회
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/worker/list")
	public Map<String, Object> workerList(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.orderWorkerList(res, param); // 알바 리스트 조회 ( 이름, 핸드폰 필터링)
		return Utils.resSet(res, param);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/worker/auth")
	public Map<String, Object> workerAuth(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.workerAuth(param); // 알바 리스트 조회 ( 이름, 핸드폰 필터링)
		return Utils.resSet(res, param);
	}
	
}
