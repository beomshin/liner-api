package com.web.liner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.web.liner.vo.AuthTb;

public interface AuthTbRepository extends JpaRepository<AuthTb, Long> {
	AuthTb findByAuthCodeAndWorkerTb_workerId(String authCode, Long workerId);
}
