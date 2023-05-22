package com.juhai.web.controller.business.request;

import lombok.Data;

@Data
public class OptUserMoneyRequest {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 金额
     */
    private String money;

    /**
     * 1：上分 2:下分
     */
    private String type;

    private String remark;
}
