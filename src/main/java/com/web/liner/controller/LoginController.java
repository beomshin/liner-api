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

import com.web.liner.request.ReqAdminLogin;
import com.web.liner.service.CommonService;
import com.web.liner.util.Utils;

@Controller
@ResponseBody
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	CommonService commonService;

	@RequestMapping(method = RequestMethod.POST, path = "/v1/admin/login")
	public Map<String, Object> adminLogin(@RequestBody ReqAdminLogin param) throws Exception { // 관리자 페이지 로그인
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		String token = commonService.adminLogin(param.getId(), param.getPw()); // 주문내역 조회
		res.put("token", token);
		return Utils.resSet(res, param.getCmd());
	}
}
