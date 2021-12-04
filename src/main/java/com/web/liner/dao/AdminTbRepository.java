package com.web.liner.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.liner.vo.AdminTb;

public interface AdminTbRepository extends JpaRepository<AdminTb, Long> {
	AdminTb findByIdAndPw(String id, String pw);

}
