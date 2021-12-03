package com.web.liner.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.liner.constants.LCons;
import com.web.liner.controller.AdminV1Controller;
import com.web.liner.dao.OrderTbRepository;
import com.web.liner.dao.WorkerTbRepository;
import com.web.liner.util.AES256Util;
import com.web.liner.vo.OrderTb;
import com.web.liner.vo.WorkerTb;

@Service
public class AdminService {

	private final Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	OrderTbRepository orderTbRepository;
	
	@Autowired
	WorkerTbRepository workerTbRepository;

	public void orderList(Map<String, Object> res, Map<String, Object> param) throws Exception { // 주문 내역 조회 서비스
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String orderCode = (String)param.get("orderCode"); // 주문 코드
		String phone = (String)param.get("phone"); // 핸드폰 번호
		String name = (String)param.get("name"); // 이름
		Date from = sdf.parse((String)param.get("from")); // 시작일
		Date to = sdf.parse((String)param.get("to")); // 종료일
		int pageNum = (int)param.get("pageNum"); // 페이지 개수
		int curPage = (int)param.get("curPage"); // 현재 페이지
	
		if(!StringUtils.isEmpty(phone)) {
			phone = new AES256Util().encrypt(phone); // 휴대폰 암호화			
		}
		
		List<OrderTb> orderList = orderTbRepository.findByOrderCodeContainingAndNameContainingAndPhoneContainingAndOrderTimeBetween(orderCode, name, phone, from, to, PageRequest.of(curPage, pageNum)); // 주문 리스트 조회
		int count = orderTbRepository.countByOrderCodeContainingAndNameContainingAndPhoneContainingAndOrderTimeBetween(orderCode, name, phone, from, to); // 주문 리스트 총 개수 조회

		for (OrderTb order : orderList) { // 핸드폰 복호화
			try {
				order.setPhone(new AES256Util().decrypt(order.getPhone()));	
			} catch (Exception e) {
				order.setPhone("핸드폰 암호화 오류");
			}
		}
	
		res.put("orderList", orderList);
		res.put("count", count);

	}
	
	public void orderWorkerList(Map<String, Object> res, Map<String, Object> param) throws Exception { // 알바 리스트ㅇ 조회 서비스
				
		String phone = (String)param.get("phone"); // 핸드폰 번호
		String name = (String)param.get("name"); // 이름
		int pageNum = (int)param.get("pageNum"); // 페이지 개수
		int curPage = (int)param.get("curPage"); // 현재 페이지
	
		if(!StringUtils.isEmpty(phone)) {
			phone = new AES256Util().encrypt(phone); // 휴대폰 암호화			
		}
		
		List<WorkerTb> workList = workerTbRepository.findByNameContainingAndPhoneContaining(name, phone, PageRequest.of(curPage, pageNum)); // 알바 리스트 조회
		int count = workerTbRepository.countByNameContainingAndPhoneContaining(name, phone); // 알바 리스트 총 개수 조회
		
		for (WorkerTb worker : workList) { // 핸드폰, 카카오톡 아이디, 계좌번호 복호화
			try {
				worker.setPhone(new AES256Util().decrypt(worker.getPhone()));
				worker.setKakaoId(new AES256Util().decrypt(worker.getKakaoId()));
				worker.setAccount(new AES256Util().decrypt(worker.getAccountTb().getAccount()));
				worker.setBank(worker.getAccountTb().getBank());
				worker.setAccountTb(null);
			} catch (Exception e) {
				worker.setAccount("계좌번호 복호화 오류");
			}
		}
	
		res.put("workList", workList);
		res.put("count", count);
		
		logger.debug("{}", res);

	}
	
}
