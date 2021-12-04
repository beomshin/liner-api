package com.web.liner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.web.liner.vo.AccountTb;
import com.web.liner.vo.AuthTb;
import com.web.liner.vo.OrderTb;

public interface AccountTbRepository extends JpaRepository<AccountTb, Long> {
}
