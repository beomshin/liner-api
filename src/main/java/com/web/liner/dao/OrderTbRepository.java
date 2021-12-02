package com.web.liner.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.liner.vo.OrderTb;

public interface OrderTbRepository extends JpaRepository<OrderTb, Long>{
	OrderTb findByOrderCode(String orderCode);
}
