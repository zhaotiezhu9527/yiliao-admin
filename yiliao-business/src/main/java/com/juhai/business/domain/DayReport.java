package com.juhai.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 日报表对象 t_day_report
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
public class DayReport
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 日期 */
    @Excel(name = "日期")
    private String today;

    /** 充值金额 */
    @Excel(name = "充值金额")
    private BigDecimal depositAmount;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal withdrawAmount;

    /** 投资金额 */
    @Excel(name = "投资金额")
    private BigDecimal investmentAmount;

    /** 收益金额 */
    @Excel(name = "收益金额")
    private BigDecimal incomeAmount;

    /** 注册人数 */
    @Excel(name = "注册人数")
    private Long registerCount;

    /** 充值人数 */
    @Excel(name = "充值人数")
    private Long depositCount;

    /** 提现人数 */
    @Excel(name = "提现人数")
    private Long withdrawCount;

    /** 投资人数 */
    @Excel(name = "投资人数")
    private Long investmentCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setToday(String today) 
    {
        this.today = today;
    }

    public String getToday() 
    {
        return today;
    }
    public void setDepositAmount(BigDecimal depositAmount) 
    {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getDepositAmount() 
    {
        return depositAmount;
    }
    public void setWithdrawAmount(BigDecimal withdrawAmount) 
    {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getWithdrawAmount() 
    {
        return withdrawAmount;
    }
    public void setInvestmentAmount(BigDecimal investmentAmount) 
    {
        this.investmentAmount = investmentAmount;
    }

    public BigDecimal getInvestmentAmount() 
    {
        return investmentAmount;
    }
    public void setIncomeAmount(BigDecimal incomeAmount) 
    {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getIncomeAmount() 
    {
        return incomeAmount;
    }
    public void setRegisterCount(Long registerCount) 
    {
        this.registerCount = registerCount;
    }

    public Long getRegisterCount() 
    {
        return registerCount;
    }
    public void setDepositCount(Long depositCount) 
    {
        this.depositCount = depositCount;
    }

    public Long getDepositCount() 
    {
        return depositCount;
    }
    public void setWithdrawCount(Long withdrawCount) 
    {
        this.withdrawCount = withdrawCount;
    }

    public Long getWithdrawCount() 
    {
        return withdrawCount;
    }
    public void setInvestmentCount(Long investmentCount) 
    {
        this.investmentCount = investmentCount;
    }

    public Long getInvestmentCount() 
    {
        return investmentCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("today", getToday())
            .append("depositAmount", getDepositAmount())
            .append("withdrawAmount", getWithdrawAmount())
            .append("investmentAmount", getInvestmentAmount())
            .append("incomeAmount", getIncomeAmount())
            .append("registerCount", getRegisterCount())
            .append("depositCount", getDepositCount())
            .append("withdrawCount", getWithdrawCount())
            .append("investmentCount", getInvestmentCount())
            .toString();
    }
}
