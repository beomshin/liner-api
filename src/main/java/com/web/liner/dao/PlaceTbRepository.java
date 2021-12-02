package com.web.liner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.liner.mapping.PlaceMapping;
import com.web.liner.vo.PlaceTb;

public interface PlaceTbRepository extends JpaRepository<PlaceTb, Long> {
}
