package com.web.liner.service;

import java.util.List;

import com.web.liner.querydsl.WorkerQuerydsl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.liner.constants.ErrorConstants;
import com.web.liner.dao.AccountTbRepository;
import com.web.liner.dao.AuthTbRepository;
import com.web.liner.dao.WorkerTbRepository;
import com.web.liner.util.AES256Util;
import com.web.liner.util.LineException;
import com.web.liner.util.Utils;
import com.web.liner.vo.AccountTb;
import com.web.liner.vo.AuthTb;
import com.web.liner.vo.WorkerTb;

@Service
public class WorkerServiceImpl implements WorkerService {

	private final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
	
	@Autowired
	WorkerTbRepository workerTbRepository;

	@Autowired
	WorkerQuerydsl workerQuerydsl;
	
	@Autowired
	AuthTbRepository authTbRepository;
	
	@Autowired
	AccountTbRepository accountTbRepository;

	@Override
	public List<WorkerTb> searchOrderWorkerList(String phone, String name) throws Exception {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(phone)) {
			phone = new AES256Util().encrypt(phone); // 휴대폰 암호화
		}

		List<WorkerTb> workList = workerTbRepository.findByNameContainingAndPhoneContainingAndAuthFlagAndState(name, phone, 1, 0); // 알바 리스트 조회

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

		return workList;
	}

	@Override
	public List<WorkerTb> searchWorkerList(String phone, String name, int authFlag, int pageNum, int curPage) throws Exception {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(phone)) {
			phone = new AES256Util().encrypt(phone); // 휴대폰 암호화			
		}
		
		List<WorkerTb> workList = workerQuerydsl.findWorkerList( phone, name, authFlag, pageNum, curPage); // 알바 리스트 조회

		for (WorkerTb worker : workList) { // 핸드폰, 카카오톡 아이디, 계좌번호 복호화
			try {
				worker.setPhone(new AES256Util().decrypt(worker.getPhone()));
				worker.setKakaoId(new AES256Util().decrypt(worker.getKakaoId()));
				worker.setAccount(new AES256Util().decrypt(worker.getAccount()));
			} catch (Exception e) {
				worker.setAccount("계좌번호 복호화 오류");
			}
		}
	
		return workList;
	}

	@Override
	public long searchWorkerListCount(String name, String phone, int authFlag) {
		// TODO Auto-generated method stub
		return workerQuerydsl.findWorkerListCount(name, phone, authFlag); // 알바 리스트 총 개수 조회
	}

	@Override
	public AuthTb verifyAuthToWorker(long workId, String authCode) throws Exception {
		// TODO Auto-generated method stub
		AuthTb authTb = authTbRepository.findByAuthCodeAndWorkerTb_workerId(authCode, workId); // 인증 조회
		if (authTb == null) { // 알바 인증 실패
			logger.error("====> [알바 인증 실패 오류]");
			throw new LineException(ErrorConstants.AUTH_FAIL, "auth fail");
		} else {
			return authTb;
		}
	}

	@Override
	public WorkerTb updateAuthWorker(long workId) throws Exception {
		WorkerTb workerTb = workerTbRepository.findByWorkerId(workId);
		workerTb.setAuthFlag(1);
		return workerTbRepository.save(workerTb);
	}

	@Override
	public WorkerTb updateStateWorker(long workId, int state) throws Exception {
		WorkerTb workerTb = workerTbRepository.findByWorkerId(workId);
		workerTb.setState(state);
		return workerTbRepository.save(workerTb);
	}


	@Override
	public String applyWorker(WorkerTb worker, AccountTb account) throws Exception {
		// TODO Auto-generated method stub
		AES256Util aes256Util = new AES256Util();
		worker.setPhone(aes256Util.encrypt(worker.getPhone()));
		worker.setKakaoId(aes256Util.encrypt(worker.getKakaoId()));
		account.setAccount(aes256Util.encrypt(account.getAccount()));
		
		logger.debug("[알바 테이블 저장]");
		worker = workerTbRepository.save(worker); // 알바 테이블 생성
		account.setWorkerId(worker.getWorkerId());
		
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

	@Override
	public String searchWorkInfo(long workId) throws Exception {
		WorkerTb workerTb = workerTbRepository.findByWorkerId(workId);
		AES256Util aes256Util = new AES256Util();
		return workerTb.getName() + "(" + Utils.phoneFormat(aes256Util.decrypt(workerTb.getPhone())) + ")";
	}

	@Override
	public WorkerTb workerCountUp(long workId) throws Exception {
		WorkerTb workerTb = workerTbRepository.findByWorkerId(workId);
		workerTb.setCount(workerTb.getCount() + 1);
		return workerTbRepository.save(workerTb);
	}

}
