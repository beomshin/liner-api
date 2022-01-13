package com.web.liner.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqWorkerAssginCancel {
    private String cmd;
    private String crc;
    private Integer workerId;
}
