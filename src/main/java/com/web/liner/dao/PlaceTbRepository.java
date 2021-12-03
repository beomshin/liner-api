package com.web.liner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.web.liner.mapping.PlaceMapping;
import com.web.liner.vo.PlaceTb;

public interface PlaceTbRepository extends JpaRepository<PlaceTb, Long> {
	List<PlaceMapping> findByBrandTb_brandId(@Param(value ="brandId") Long brandId);
}
