package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_deposit
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public class Deposit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 操作金额 */
    @Excel(name = "操作金额")
    private BigDecimal optAmount;

    /** 操作前金额 */
    @Excel(name = "操作前金额")
    private BigDecimal beforeAmount;

    /** 操作后金额 */
    @Excel(name = "操作后金额")
    private BigDecimal afterAmount;

    /** 1:系统充值 */
    @Excel(name = "1:系统充值", readConverterExp = "1=系统充值")
    private Long optType;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    /** 状态(0:待审核 1:审核通过) */
    @Excel(name = "状态", readConverterExp = "0=待审核,1=审核通过")
    private Long status;

    /** 操作人 */
    @Excel(name = "操作人")
    private String optUser;

    /** 上级代理 */
//    @Excel(name = "上级代理")
    private String userAgent;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setOptAmount(BigDecimal optAmount) 
    {
        this.optAmount = optAmount;
    }

    public BigDecimal getOptAmount() 
    {
        return optAmount;
    }
    public void setBeforeAmount(BigDecimal beforeAmount) 
    {
        this.beforeAmount = beforeAmount;
    }

    public BigDecimal getBeforeAmount() 
    {
        return beforeAmount;
    }
    public void setAfterAmount(BigDecimal afterAmount) 
    {
        this.afterAmount = afterAmount;
    }

    public BigDecimal getAfterAmount() 
    {
        return afterAmount;
    }
    public void setOptType(Long optType) 
    {
        this.optType = optType;
    }

    public Long getOptType() 
    {
        return optType;
    }
    public void setOptTime(Date optTime) 
    {
        this.optTime = optTime;
    }

    public Date getOptTime() 
    {
        return optTime;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setOptUser(String optUser) 
    {
        this.optUser = optUser;
    }

    public String getOptUser() 
    {
        return optUser;
    }
    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("userName", getUserName())
            .append("realName", getRealName())
            .append("optAmount", getOptAmount())
            .append("beforeAmount", getBeforeAmount())
            .append("afterAmount", getAfterAmount())
            .append("optType", getOptType())
            .append("optTime", getOptTime())
            .append("status", getStatus())
            .append("optUser", getOptUser())
            .append("userAgent", getUserAgent())
            .toString();
    }
}
