package com.web.liner.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.web.liner.controller.MbV1Controller;
import com.web.liner.dao.AccountTbRepository;
import com.web.liner.dao.AuthTbRepository;
import com.web.liner.dao.BankTbRepository;
import com.web.liner.dao.BrandTbRepository;
import com.web.liner.dao.OrderTbRepository;
import com.web.liner.dao.PlaceTbRepository;
import com.web.liner.dao.WorkerTbRepository;
import com.web.liner.mapping.BrandMapping;
import com.web.liner.mapping.PlaceMapping;
import com.web.liner.util.AES256Util;
import com.web.liner.util.Utils;
import com.web.liner.vo.AccountTb;
import com.web.liner.vo.AuthTb;
import com.web.liner.vo.BankTb;
import com.web.liner.vo.BrandTb;
import com.web.liner.vo.OrderTb;
import com.web.liner.vo.PlaceTb;
import com.web.liner.vo.WorkerTb;

@Service
public class MbService {
	
	private final Logger logger = LoggerFactory.getLogger(MbService.class);
	
	@Autowired
	PlaceTbRepository placeTbRepository;

	@Autowired
	BrandTbRepository brandTbRepository;
	
	@Autowired
	OrderTbRepository orderTbRepository;
	
	@Autowired
	BankTbRepository bankTbRepository;

	@Autowired
	AuthTbRepository authTbRepository;
	
	@Autowired
	WorkerTbRepository workerTbRepository;

	@Autowired
	AccountTbRepository accountTbRepository;
	
	public List<BrandTb> searchPlaceAndBarnd() throws Exception { // 장소 & 브랜드 찾기
		
		logger.debug("[장초 찾기 findAll]");
		List<BrandTb> brandList = brandTbRepository.findAll(); // 장소 찾기
		for(BrandTb brand : brandList) {
			logger.debug("[브랜드찾기 찾기 findByPlaceTb_placeId]");
			List<PlaceMapping> places = placeTbRepository.findByBrandTb_brandId(brand.getBrandId()); // 장소별 브랜드 찾기
			brand.setPlaces(places);
		}
		
		return brandList;
	}
	
	public OrderTb applyLineService(OrderTb order) throws Exception { // 주문내역 저장 서비스
		
		AES256Util aes256Util = new AES256Util();
		order.setPhone(aes256Util.encrypt(order.getPhone()));
		order.setOrderCode(Utils.createOrderCode());
		logger.debug("[구매 신청하기 주문 저장 데이터] : {}", order);
		
		return orderTbRepository.save(order);
	}
	
	public OrderTb checkLineService(String orderCode) throws Exception { // 주문 정보 찾기

		AES256Util aes256Util = new AES256Util();

		logger.debug("[orderCode 통해 주문내역 찾기 findByOrderCode]");
		OrderTb orderTb = orderTbRepository.findByOrderCode(orderCode);
		orderTb.setPhone(aes256Util.decrypt(orderTb.getPhone()));
		return orderTb; // orderCode로 주문내역 찾기
	}
	
	@Transactional
	public String applyLineWorker(WorkerTb worker, AccountTb account) throws Exception { // 알바 신청 저장
		
		AES256Util aes256Util = new AES256Util();
		worker.setPhone(aes256Util.encrypt(worker.getPhone()));
		worker.setKakaoId(aes256Util.encrypt(worker.getKakaoId()));
		
		logger.debug("[알바 테이블 저장]");
		worker = workerTbRepository.save(worker); // 알바 테이블 생성
		account.setWorkerTb(worker);
		
		logger.debug("[계좌번호 테이블 저장]");
		account = accountTbRepository.save(account);// 계좌번호 테이블 생성
		worker.setAccountTb(account);

		logger.debug("[알바 테이블 계좌 테이블 매핑]");
		worker = workerTbRepository.save(worker); // 알바 테이블 계좌 테이블 매핑

		AuthTb authTb = new AuthTb();
		String authCode =  Utils.createAuthCode(); // 12자 authCode 생성
		authTb.setAuthCode(authCode); 
		authTb.setWorkerTb(worker);
		authTbRepository.save(authTb); // 인증 테이블 생성
		
		return authCode;
	}
	
	public List<BankTb> searchBank() { // 은행 정보 찾기
		return bankTbRepository.findAll(); // 은행 찾기
	}
	
	
}
