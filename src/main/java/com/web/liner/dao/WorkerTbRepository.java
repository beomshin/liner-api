package com.web.liner.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.liner.vo.OrderTb;
import com.web.liner.vo.WorkerTb;

public interface WorkerTbRepository extends JpaRepository<WorkerTb, Long> {
	List<WorkerTb> findByNameContainingAndPhoneContaining(String name, String phone);
	List<WorkerTb> findByNameContainingAndPhoneContaining(String name, String phone, Pageable pageable);
	int countByNameContainingAndPhoneContaining(String name, String phone);

}
