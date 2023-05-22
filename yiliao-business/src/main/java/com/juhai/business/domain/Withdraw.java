package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_withdraw
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public class Withdraw extends BaseEntity
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

    /** 操作金额 */
    @Excel(name = "操作金额")
    private BigDecimal optAmount;

    /** 操作前金额 */
    @Excel(name = "操作前金额")
    private BigDecimal beforeAmount;

    /** 操作后金额 */
    @Excel(name = "操作后金额")
    private BigDecimal afterAmount;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String walletAddr;

    /** 银行卡号 */
    @Excel(name = "银行卡号")
    private String bankCardNum;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 开户行地址 */
    @Excel(name = "开户行地址")
    private String bankAddr;

    /** 1:提现银行 2:提现钱包 */
    @Excel(name = "提现类型", readConverterExp = "1=提现到银行卡,2=提现到钱包")
    private Long optType;

    /** 0:待审核 1:审核通过 2:审核不通过 */
    @Excel(name = "状态", readConverterExp = "0:待审核,1=提现成功,2=提现失败")
    private Long status;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

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
    public void setWalletAddr(String walletAddr) 
    {
        this.walletAddr = walletAddr;
    }

    public String getWalletAddr() 
    {
        return walletAddr;
    }
    public void setBankCardNum(String bankCardNum) 
    {
        this.bankCardNum = bankCardNum;
    }

    public String getBankCardNum() 
    {
        return bankCardNum;
    }
    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }
    public void setBankAddr(String bankAddr) 
    {
        this.bankAddr = bankAddr;
    }

    public String getBankAddr() 
    {
        return bankAddr;
    }
    public void setOptType(Long optType) 
    {
        this.optType = optType;
    }

    public Long getOptType() 
    {
        return optType;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setOptTime(Date optTime) 
    {
        this.optTime = optTime;
    }

    public Date getOptTime() 
    {
        return optTime;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("userName", getUserName())
            .append("optAmount", getOptAmount())
            .append("beforeAmount", getBeforeAmount())
            .append("afterAmount", getAfterAmount())
            .append("walletAddr", getWalletAddr())
            .append("bankCardNum", getBankCardNum())
            .append("bankName", getBankName())
            .append("bankAddr", getBankAddr())
            .append("optType", getOptType())
            .append("status", getStatus())
            .append("optTime", getOptTime())
            .append("checkTime", getCheckTime())
            .append("operator", getOperator())
            .append("userAgent", getUserAgent())
            .append("remark", getRemark())
            .toString();
    }
}
