package com.juhai.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_paramter
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public class Paramter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 参数名 */
    @Excel(name = "参数名")
    private String paramName;

    /** 参数key */
    @Excel(name = "参数key")
    private String paramKey;

    /** 参数值 */
    @Excel(name = "参数值")
    private String paramValue;

    /** 是否展示前端(0:是 1:否) */
    @Excel(name = "是否展示前端(0:是 1:否)")
    private Long isShow;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParamName(String paramName) 
    {
        this.paramName = paramName;
    }

    public String getParamName() 
    {
        return paramName;
    }
    public void setParamKey(String paramKey) 
    {
        this.paramKey = paramKey;
    }

    public String getParamKey() 
    {
        return paramKey;
    }
    public void setParamValue(String paramValue) 
    {
        this.paramValue = paramValue;
    }

    public String getParamValue() 
    {
        return paramValue;
    }
    public void setIsShow(Long isShow) 
    {
        this.isShow = isShow;
    }

    public Long getIsShow() 
    {
        return isShow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("paramName", getParamName())
            .append("paramKey", getParamKey())
            .append("paramValue", getParamValue())
            .append("isShow", getIsShow())
            .toString();
    }
}
