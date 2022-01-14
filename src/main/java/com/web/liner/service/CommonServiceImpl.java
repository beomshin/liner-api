package com.web.liner.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.web.liner.util.AES256Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.liner.constants.ErrorConstants;
import com.web.liner.constants.LCons;
import com.web.liner.dao.AdminTbRepository;
import com.web.liner.dao.BankTbRepository;
import com.web.liner.dao.BrandTbRepository;
import com.web.liner.dao.PlaceTbRepository;
import com.web.liner.mapping.PlaceMapping;
import com.web.liner.util.JwtGenerator;
import com.web.liner.util.LineException;
import com.web.liner.vo.AdminTb;
import com.web.liner.vo.BankTb;
import com.web.liner.vo.BrandTb;

@Service
public class CommonServiceImpl implements CommonService{

	private final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	PlaceTbRepository placeTbRepository;

	@Autowired
	BrandTbRepository brandTbRepository;
	
	@Autowired
	BankTbRepository bankTbRepository;
	
	@Autowired
	AdminTbRepository adminTbRepository;
	
	@Override
	public List<BrandTb> searchBrands() {
		logger.debug("[장초 찾기 findAll]");
		List<BrandTb> brandList = brandTbRepository.findAll(); // 장소 찾기
		for(BrandTb brand : brandList) {
			logger.debug("[브랜드찾기 찾기 findByPlaceTb_placeId]");
			List<PlaceMapping> places = placeTbRepository.findByBrandTb_brandId(brand.getBrandId()); // 장소별 브랜드 찾기
			brand.setPlaces(places);
		}
		return brandList;
	}

	@Override
	public List<BankTb> searchBanks() {
		return bankTbRepository.findAll(); // 은행 찾기
	}

	@Override
	public String adminLogin(String id, String pw) throws LineException, UnsupportedEncodingException, GeneralSecurityException {
		AES256Util aes256Util = new AES256Util();
		AdminTb adminTb = adminTbRepository.findByIdAndPw(id, aes256Util.encrypt(pw));
		if (adminTb == null) { // 과리자 페이지 로그인 실패
			logger.error("====> [관리자 페이지 로그인 실패]");
			throw new LineException(ErrorConstants.ADMIN_LOGIN_FAIL, "admin login fail");
		} else {
			JwtGenerator jwtGenerator = new JwtGenerator(); // JWT module
			return jwtGenerator.createJWT(id, LCons.LINER, null, LCons.ONE_HOUR ); // 토큰 생성 (만료 1시간)
		}
	}

}
