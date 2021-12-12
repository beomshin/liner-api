package com.web.liner.request;

import com.web.liner.vo.OrderTb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqApplyService {

	private String cmd;
	private String crc;
	private OrderTb order;
	
}
