package com.web.liner.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.liner.constants.LCons;
import com.web.liner.dao.OrderTbRepository;
import com.web.liner.service.AdminService;
import com.web.liner.util.AES256Util;
import com.web.liner.util.Utils;
import com.web.liner.vo.OrderTb;

@Controller
@ResponseBody
public class AdminV1Controller {

	private final Logger logger = LoggerFactory.getLogger(AdminV1Controller.class);

	@Autowired
	OrderTbRepository orderTbRepository;
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/login")
	public String adminLogin() {
		return "adminLogin";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/order/list")
	public Map<String, Object> orderList(@RequestBody Map<String, Object> param) throws Exception { // 주문 내역 조회 컨트롤러
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.orderList(res, param); // 주문내역 조회
		return Utils.resSet(res, param);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/order/worker/list")
	public Map<String, Object> orderWorkerList(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		adminService.orderWorkerList(res, param); // 주문내역 조회
		return Utils.resSet(res, param);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/order/worker/assign")
	public String orderWorkerAssign(@RequestBody Map<String, Object> param) {
		return "orderWorkerAssign";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/worker/list")
	public String workerList(@RequestBody Map<String, Object> param) {
		return "workerList";
	}

	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/worker/auth")
	public String workerAuth(@RequestBody Map<String, Object> param) {
		return "workerAuth";
	}
	
}
