package com.web.liner.dao;

import java.awt.print.Pageable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.web.liner.mapping.BrandMapping;
import com.web.liner.mapping.PlaceMapping;
import com.web.liner.vo.BrandTb;

@Transactional
public interface BrandTbRepository extends JpaRepository<BrandTb, Long>{
}
