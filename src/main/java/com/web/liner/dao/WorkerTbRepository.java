package com.web.liner.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.liner.vo.OrderTb;
import com.web.liner.vo.WorkerTb;

public interface WorkerTbRepository extends JpaRepository<WorkerTb, Long> {
	List<WorkerTb> findByNameContainingAndPhoneContainingAndAuthFlagAndState(String name, String phone, int authFlag, int state);
	List<WorkerTb> findByNameContainingAndPhoneContainingAndState(String name, String phone, Pageable pageable, int state);
	int countByNameContainingAndPhoneContaining(String name, String phone);
	WorkerTb findByWorkerId(long workerId);
}
