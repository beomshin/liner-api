package com.web.liner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminV1Controller {

	private final Logger logger = LoggerFactory.getLogger(AdminV1Controller.class);

	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/login")
	public String adminLogin() {
		return "adminLogin";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/order/list")
	public String orderList() {
		return "orderList";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/order/worker/list")
	public String orderWorkerList() {
		return "orderWorkerList";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/order/worker/assign")
	public String orderWorkerAssign() {
		return "orderWorkerAssign";
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/worker/list")
	public String workerList() {
		return "workerList";
	}

	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/worker/auth")
	public String workerAuth() {
		return "workerAuth";
	}
	
}
