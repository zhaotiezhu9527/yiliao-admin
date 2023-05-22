package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_order
 * 
 * @author zhaotiezhu
 * @date 2023-05-22
 */
public class Order extends BaseEntity
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

    /** 项目ID */
//    @Excel(name = "项目ID")
    private Long projectId;

    /** 项目名 */
    @Excel(name = "项目名")
    private String projectName;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 投资金额 */
    @Excel(name = "投资金额")
    private BigDecimal amount;

    /** 收益率 */
    @Excel(name = "收益率")
    private BigDecimal incomeRate;

    /** 期限(分钟) */
    @Excel(name = "期限(分钟)")
    private Long limitTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 预计返款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预计返款时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date forecastReturnTime;

    /** 实际返款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际返款时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date actualReturnTime;

    /** 预计返款利息 */
    @Excel(name = "预计返款利息")
    private BigDecimal forecastReturnAmount;

    /** 实际返款利息 */
    @Excel(name = "实际返款利息")
    private BigDecimal actualReturnAmount;

    /** 0:未结算 1:已结算 2:结算异常 */
    @Excel(name = "状态",readConverterExp = "0:未结算,1=已结算")
    private Long status;

    /** 上级代理 */
//    @Excel(name = "上级代理")
    private String userAgent;

    /** 订单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

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
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setIncomeRate(BigDecimal incomeRate) 
    {
        this.incomeRate = incomeRate;
    }

    public BigDecimal getIncomeRate() 
    {
        return incomeRate;
    }
    public void setLimitTime(Long limitTime) 
    {
        this.limitTime = limitTime;
    }

    public Long getLimitTime() 
    {
        return limitTime;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setForecastReturnTime(Date forecastReturnTime) 
    {
        this.forecastReturnTime = forecastReturnTime;
    }

    public Date getForecastReturnTime() 
    {
        return forecastReturnTime;
    }
    public void setActualReturnTime(Date actualReturnTime) 
    {
        this.actualReturnTime = actualReturnTime;
    }

    public Date getActualReturnTime() 
    {
        return actualReturnTime;
    }
    public void setForecastReturnAmount(BigDecimal forecastReturnAmount) 
    {
        this.forecastReturnAmount = forecastReturnAmount;
    }

    public BigDecimal getForecastReturnAmount() 
    {
        return forecastReturnAmount;
    }
    public void setActualReturnAmount(BigDecimal actualReturnAmount) 
    {
        this.actualReturnAmount = actualReturnAmount;
    }

    public BigDecimal getActualReturnAmount() 
    {
        return actualReturnAmount;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("userName", getUserName())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("realName", getRealName())
            .append("amount", getAmount())
            .append("incomeRate", getIncomeRate())
            .append("limitTime", getLimitTime())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("forecastReturnTime", getForecastReturnTime())
            .append("actualReturnTime", getActualReturnTime())
            .append("forecastReturnAmount", getForecastReturnAmount())
            .append("actualReturnAmount", getActualReturnAmount())
            .append("status", getStatus())
            .append("userAgent", getUserAgent())
            .append("orderTime", getOrderTime())
            .toString();
    }
}
