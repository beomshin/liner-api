package com.web.liner.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.liner.util.LineException;
import com.web.liner.vo.BankTb;
import com.web.liner.vo.BrandTb;

@Service
public interface CommonService {
	List<BrandTb> searchBrands(); // 브랜드별 장소 리스트 찾기
	List<BankTb> searchBanks(); // 은행 리스트 찾기
	String adminLogin(String id, String pw) throws LineException, UnsupportedEncodingException, GeneralSecurityException;
}
