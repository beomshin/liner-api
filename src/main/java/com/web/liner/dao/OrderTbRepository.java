package com.web.liner.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.liner.vo.OrderTb;


public interface OrderTbRepository extends JpaRepository<OrderTb, Long>{
	OrderTb findByOrderCode(String orderCode);
	List<OrderTb> findByOrderCodeContainingAndNameContainingAndPhoneContainingAndOrderTimeBetween(String orderCode, String name, String phone, Date from, Date to, Pageable pageable);
	int countByOrderCodeContainingAndNameContainingAndPhoneContainingAndOrderTimeBetween(String orderCode, String name, String phone, Date from, Date to);

}
