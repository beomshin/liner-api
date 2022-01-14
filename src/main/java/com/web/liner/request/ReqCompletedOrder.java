package com.web.liner.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCompletedOrder {
    private String cmd;
    private String crc;
    private Integer orderId;
}
