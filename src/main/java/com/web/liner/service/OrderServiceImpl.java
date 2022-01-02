package com.web.liner.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.liner.dao.ConfigTbRepository;
import com.web.liner.dao.OrderTbRepository;
import com.web.liner.util.AES256Util;
import com.web.liner.util.Utils;
import com.web.liner.vo.ConfigTb;
import com.web.liner.vo.OrderTb;
import com.web.liner.vo.WorkerTb;

@Service
public class OrderServiceImpl implements OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderTbRepository orderTbRepository;
	
	@Autowired
	ConfigTbRepository configTbRepository;
	
	@Override
	@Transactional
	public OrderTb applyOrder(OrderTb order) throws Exception {
		AES256Util aes256Util = new AES256Util();
		order.setPhone(aes256Util.encrypt(order.getPhone()));
		ConfigTb configTb = configTbRepository.findByKey("pickNo");
		order.setOrderCode(configTb.getValue());
		configTbRepository.updatePickNo(String.valueOf(Integer.parseInt(configTb.getValue()) + 1));
		logger.debug("[구매 신청하기 주문 저장 데이터] : {}", order);
		
		return orderTbRepository.save(order);
	}

	@Override
	public OrderTb searchOrder(String orderCode) throws Exception {
		AES256Util aes256Util = new AES256Util();
		logger.debug("[orderCode 통해 주문내역 찾기 findByOrderCode]");
		OrderTb orderTb = orderTbRepository.findByOrderCode(orderCode);
		orderTb.setPhone(aes256Util.decrypt(orderTb.getPhone()));
		return orderTb; // orderCode로 주문내역 찾기
	}

	@Override
	public List<OrderTb> searchOrderList(String orderCode, String phone, String name, String from, String to, int pageNum,
			int curPage) throws Exception {
		// TODO Auto-generated method stub

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if(!StringUtils.isEmpty(phone)) {
			phone = new AES256Util().encrypt(phone); // 휴대폰 암호화			
		}
		
		List<OrderTb> orderList = orderTbRepository.findByOrderCodeContainingAndNameContainingAndPhoneContainingAndOrderTimeBetween(orderCode, name, phone, sdf.parse(from), sdf.parse(to), PageRequest.of(curPage, pageNum)); // 주문 리스트 조회

		for (OrderTb order : orderList) { // 핸드폰 복호화
			try {
				order.setPhone(new AES256Util().decrypt(order.getPhone()));	
			} catch (Exception e) {
				order.setPhone("핸드폰 암호화 오류");
			}
		}
		
		return orderList;
	}

	@Override
	public OrderTb assignWorkerToOrder(long workerId, long orderId) {
		// TODO Auto-generated method stub
		OrderTb orderTb = orderTbRepository.findByOrderId(orderId); // ordertb 조회
		WorkerTb workerTb = new WorkerTb();
		workerTb.setWorkerId(workerId); // 외래키 workerId 세팅
		orderTb.setWorkerTb(workerTb);
		return orderTbRepository.save(orderTb); // update
	}


	@Override
	public int searchOrderListCount(String orderCode, String phone, String name, String from, String to)
			throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return orderTbRepository.countByOrderCodeContainingAndNameContainingAndPhoneContainingAndOrderTimeBetween(orderCode, name, phone, sdf.parse(from), sdf.parse(to)); // 주문 리스트 총 개수 조회
	}

}
