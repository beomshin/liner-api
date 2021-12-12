package com.web.liner.request;

import com.web.liner.vo.AccountTb;
import com.web.liner.vo.WorkerTb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqApplyWorker {

	private String cmd;
	private String crc;
	private WorkerTb worker;
	private AccountTb bankInfo;
}
