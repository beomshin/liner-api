package com.web.liner.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReqWorkerCancel {

    private String cmd;
    private String crc;
    private Integer orderId;

}
