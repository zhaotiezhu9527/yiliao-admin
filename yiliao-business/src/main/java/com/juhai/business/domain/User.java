package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 t_user
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 用户余额 */
    @Excel(name = "用户余额")
    private BigDecimal balance;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idCard;

    /** 登录密码 */
//    @Excel(name = "登录密码")
    private String loginPwd;

    /** 支付密码 */
//    @Excel(name = "支付密码")
    private String payPwd;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String inviteCode;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String walletAddr;

    /** 银行卡号 */
    @Excel(name = "银行卡号")
    private String bankCardNum;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 开户行地址 */
    @Excel(name = "开户行地址")
    private String bankAddr;

    /** 用户状态(0:正常 1:冻结) */
    @Excel(name = "状态", readConverterExp = "0=正常,1=冻结")
    private Long userStatus;

    /** 用户等级id */
//    @Excel(name = "用户等级id")
    private Long userLevelId;

    /** 上级代理用户名 */
    @Excel(name = "上级用户")
    private String userAgent;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /** 注册Ip */
    @Excel(name = "注册Ip")
    private String registerIp;

    /** 上次登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /** 上次登录IP */
    @Excel(name = "上次登录IP")
    private String lastIp;

    /** 最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

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
    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }
    public void setLoginPwd(String loginPwd) 
    {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwd() 
    {
        return loginPwd;
    }
    public void setPayPwd(String payPwd) 
    {
        this.payPwd = payPwd;
    }

    public String getPayPwd() 
    {
        return payPwd;
    }
    public void setInviteCode(String inviteCode) 
    {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() 
    {
        return inviteCode;
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
    public void setUserStatus(Long userStatus) 
    {
        this.userStatus = userStatus;
    }

    public Long getUserStatus() 
    {
        return userStatus;
    }
    public void setUserLevelId(Long userLevelId) 
    {
        this.userLevelId = userLevelId;
    }

    public Long getUserLevelId() 
    {
        return userLevelId;
    }
    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }
    public void setRegisterTime(Date registerTime)
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
    }
    public void setRegisterIp(String registerIp) 
    {
        this.registerIp = registerIp;
    }

    public String getRegisterIp() 
    {
        return registerIp;
    }
    public void setLastTime(Date lastTime) 
    {
        this.lastTime = lastTime;
    }

    public Date getLastTime() 
    {
        return lastTime;
    }
    public void setLastIp(String lastIp) 
    {
        this.lastIp = lastIp;
    }

    public String getLastIp() 
    {
        return lastIp;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("balance", getBalance())
            .append("realName", getRealName())
            .append("idCard", getIdCard())
            .append("loginPwd", getLoginPwd())
            .append("payPwd", getPayPwd())
            .append("inviteCode", getInviteCode())
            .append("walletAddr", getWalletAddr())
            .append("bankCardNum", getBankCardNum())
            .append("bankName", getBankName())
            .append("bankAddr", getBankAddr())
            .append("userStatus", getUserStatus())
            .append("userLevelId", getUserLevelId())
            .append("userAgent", getUserAgent())
            .append("registerTime", getRegisterTime())
            .append("registerIp", getRegisterIp())
            .append("lastTime", getLastTime())
            .append("lastIp", getLastIp())
            .append("modifyTime", getModifyTime())
            .toString();
    }
}
