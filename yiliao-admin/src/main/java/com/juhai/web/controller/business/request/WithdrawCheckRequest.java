package com.juhai.web.controller.business.request;

import lombok.Data;

@Data
public class WithdrawCheckRequest {

    /**
     * 提现单ID
     */
    private String id;

    /**
     * 1：通过 2:拒绝
     */
    private String status;

    private String remark;
}
