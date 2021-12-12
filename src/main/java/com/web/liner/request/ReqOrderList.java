package com.web.liner.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderList {

	private String cmd;
	private String crc;
	private String from;
	private String to;
	private String orderCode;
	private String name;
	private String phone;
	private Integer curPage;
	private Integer pageNum;
	
}
