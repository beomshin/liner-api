package com.web.liner.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqWorkerAuth {

	private String cmd;
	private String crc;
	private String token;
	private Integer workerId;
	private String authCode;
}
