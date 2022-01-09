package com.web.liner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.liner.vo.AccountTb;
import com.web.liner.vo.AuthTb;
import com.web.liner.vo.WorkerTb;

@Service
public interface WorkerService {
	List<WorkerTb> searchOrderWorkerList(String phone, String name) throws Exception;
	List<WorkerTb> searchWorkerList(String phone, String name, int state, int pageNum, int curPage) throws Exception;
	int searchWorkerListCount(String name, String phone);
	AuthTb verifyAuthToWorker(long workId, String authCode) throws Exception;
	WorkerTb updateAuthWorker(long workId) throws Exception;
	WorkerTb updateStateWorker(long workId, int state) throws  Exception;
	String applyWorker(WorkerTb worker, AccountTb account) throws Exception;
	String searchWorkInfo(long workId) throws  Exception;
}
