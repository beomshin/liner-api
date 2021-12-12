package com.web.liner.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqOrderWorker {

	private String cmd;
	private String crc;
	private String name;
	private String phone;
	private Integer curPage;
	private Integer pageNum;
	
}
