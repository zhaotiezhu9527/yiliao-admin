package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_account
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public class Account extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 流水号 */
    @Excel(name = "流水号")
    private String accountNo;

    /** 操作金额 */
    @Excel(name = "操作金额")
    private BigDecimal optAmount;

    /** 操作前金额 */
    @Excel(name = "操作前金额")
    private BigDecimal beforeAmount;

    /** 操作后金额 */
    @Excel(name = "操作后金额")
    private BigDecimal afterAmount;

    /** 1:收入 2:支出 */
    @Excel(name = "收支类型", readConverterExp = "1=收入,2=支出")
    private Long type;

    /** 1:系统充值 2:系统扣款 3:提现 4:投资扣款 5:投资进账 6:签到 */
//    @Excel(name = "1:系统充值 2:系统扣款 3:提现 4:投资扣款 5:投资进账 6:签到")
    private Long optType;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    /** 上级代理 */
//    @Excel(name = "上级代理")
    private String userAgent;

    /** 关联订单号 */
    @Excel(name = "关联订单号")
    private String refNo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
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
    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }
    public void setRefNo(String refNo) 
    {
        this.refNo = refNo;
    }

    public String getRefNo() 
    {
        return refNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
                .append("accountNo", getAccountNo())
            .append("userName", getUserName())
            .append("optAmount", getOptAmount())
            .append("beforeAmount", getBeforeAmount())
            .append("afterAmount", getAfterAmount())
            .append("type", getType())
            .append("optType", getOptType())
            .append("optTime", getOptTime())
            .append("userAgent", getUserAgent())
            .append("refNo", getRefNo())
            .append("remark", getRemark())
            .toString();
    }
}
