package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_project
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String projectName;

    /** 项目金额(万元) */
    @Excel(name = "项目金额(万元)")
    private BigDecimal projectAmount;

    /** 收益率 */
    @Excel(name = "收益率")
    private BigDecimal incomeRate;

    /** 期限(分钟) */
    @Excel(name = "期限(分钟)")
    private Long limitTime;

    /** 起投金额 */
    @Excel(name = "起投金额")
    private BigDecimal minAmount;

    /** 0:开启 1:关闭 */
    @Excel(name = "0:开启 1:关闭")
    private Long status;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 项目进度 */
    @Excel(name = "项目进度")
    private String schedule;

    /** 担保公司 */
    @Excel(name = "担保公司")
    private String guaranteeCompany;

    /** 排序号(值越大越靠前) */
    @Excel(name = "排序号(值越大越靠前)")
    private Long sort;

    /** 最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String img;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setProjectAmount(BigDecimal projectAmount) 
    {
        this.projectAmount = projectAmount;
    }

    public BigDecimal getProjectAmount() 
    {
        return projectAmount;
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
    public void setMinAmount(BigDecimal minAmount) 
    {
        this.minAmount = minAmount;
    }

    public BigDecimal getMinAmount() 
    {
        return minAmount;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
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
    public void setSchedule(String schedule) 
    {
        this.schedule = schedule;
    }

    public String getSchedule() 
    {
        return schedule;
    }
    public void setGuaranteeCompany(String guaranteeCompany) 
    {
        this.guaranteeCompany = guaranteeCompany;
    }

    public String getGuaranteeCompany() 
    {
        return guaranteeCompany;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectName", getProjectName())
            .append("projectAmount", getProjectAmount())
            .append("incomeRate", getIncomeRate())
            .append("limitTime", getLimitTime())
            .append("minAmount", getMinAmount())
            .append("status", getStatus())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("schedule", getSchedule())
            .append("guaranteeCompany", getGuaranteeCompany())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .append("img", getImg())
            .toString();
    }
}
