package com.web.liner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.liner.vo.ConfigTb;

public interface ConfigTbRepository extends JpaRepository<ConfigTb, Long> {
	ConfigTb findByKey(String key);
	
	@Modifying
	@Query("UPDATE configtb SET VALUE = :no WHERE key = \'pickNo\'")
	void updatePickNo(@Param("no") String no); // 픽업넘버 증가
}
