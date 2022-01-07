package com.web.liner.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.liner.dao.OrderTbRepository;
import com.web.liner.request.ReqOrderList;
import com.web.liner.request.ReqOrderWorker;
import com.web.liner.request.ReqWorkerAssign;
import com.web.liner.request.ReqWorkerAuth;
import com.web.liner.service.OrderService;
import com.web.liner.service.WorkerService;
import com.web.liner.util.Utils;
import com.web.liner.vo.OrderTb;
import com.web.liner.vo.WorkerTb;

@Controller
@ResponseBody
@RequestMapping("/v1/admin")
@CrossOrigin("*")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	OrderTbRepository orderTbRepository;

	@Autowired
	OrderService orderService;

	@Autowired
	WorkerService workerService;	
	
	@RequestMapping(method = RequestMethod.POST, path = "/order/list")
	public Map<String, Object> orderList(@RequestBody ReqOrderList param) throws Exception { // 주문 내역 조회 컨트롤러
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		List<OrderTb> orderList = orderService.searchOrderList(param.getOrderCode(), param.getPhone(), param.getName(), param.getFrom(), param.getTo(), param.getPageNum(), param.getCurPage());
		int count  = orderService.searchOrderListCount(param.getOrderCode(), param.getPhone(), param.getName(), param.getFrom(), param.getTo());
		for (OrderTb orderTb : orderList) {
			if (orderTb.getWorkerTb() != null) {
				orderTb.setWorkerInfo(workerService.searchWorkInfo(orderTb.getWorkerTb().getWorkerId()));
				orderTb.setWorkerTb(null);
			}
		}
		res.put("orderList", orderList);
		res.put("count", count);
		return Utils.resSet(res, param.getCmd());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/order/worker/list")
	public Map<String, Object> orderWorkerList(@RequestBody ReqOrderWorker param) throws Exception { // 알바 리스트 조회 
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		List<WorkerTb> workerList = workerService.searchOrderWorkerList(param.getPhone(), param.getName());
//		int count = workerService.searchWorkerListCount(param.getName(), param.getPhone());
		res.put("workerList", workerList);
//		res.put("count", count);
		return Utils.resSet(res, param.getCmd());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/order/worker/assign")
	public Map<String, Object>  orderWorkerAssign(@RequestBody ReqWorkerAssign param) throws Exception{ // 알바 배정
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		orderService.assignWorkerToOrder(Long.valueOf(param.getWorkerId()), Long.valueOf(param.getOrderId()));
		String workerInfo = workerService.searchWorkInfo(Long.valueOf(param.getWorkerId()));
		workerService.updateStateWorker(param.getWorkerId());
		res.put("workerInfo", workerInfo);
		return Utils.resSet(res, param.getCmd());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/worker/list")
	public Map<String, Object> workerList(@RequestBody ReqOrderWorker param) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		List<WorkerTb> workerList = workerService.searchWorkerList(param.getPhone(), param.getName(), param.getPageNum(), param.getCurPage());
		int count = workerService.searchWorkerListCount(param.getName(), param.getPhone());
		res.put("workerList", workerList);
		res.put("count", count);
		return Utils.resSet(res, param.getCmd());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/worker/auth")
	public Map<String, Object> workerAuth(@RequestBody ReqWorkerAuth param) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>(); // res map
		workerService.verifyAuthToWorker(Long.valueOf(param.getWorkerId()), param.getAuthCode());
		workerService.updateAuthWorker(param.getWorkerId());
		return Utils.resSet(res, param.getCmd());
	}
	
}
