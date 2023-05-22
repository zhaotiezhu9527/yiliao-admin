package com.juhai.web.controller.business;
import java.math.BigDecimal;
import java.util.Date;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import com.juhai.business.domain.Account;
import com.juhai.business.domain.Deposit;
import com.juhai.business.domain.Withdraw;
import com.juhai.business.service.*;
import com.juhai.web.controller.business.request.OptUserMoneyRequest;
import org.apache.commons.collections4.queue.PredicatedQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.enums.BusinessType;
import com.juhai.business.domain.User;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/business/user")
public class UserController extends BaseController
{
    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private IParamterService paramterService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(User user)
    {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:export')")
    @Log(title = "【用户导出】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, User user)
    {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userService.selectUserById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:user:add')")
    @Log(title = "【用户新增】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody User user)
    {
        // 校验用户名 密码 支付密码
        boolean matchUserName = ReUtil.isMatch("^[a-zA-Z0-9]{4,12}$", user.getUserName());
        if (!matchUserName) {
            return AjaxResult.error("请输入4-12位用户名");
        }
        boolean matchLoginPwd = ReUtil.isMatch("^[a-zA-Z0-9]{6,12}$", user.getLoginPwd());
        if (!matchLoginPwd) {
            return AjaxResult.error("请输入6-12位登录密码");
        }
        boolean matchPayPwd = ReUtil.isMatch("^\\d{6}$", user.getPayPwd());
        if (!matchPayPwd) {
            return AjaxResult.error("请输入6位支付密码");
        }
        // 查询用户名是否存在
        User existUser = userService.getUserByName(user.getUserName());
        if (existUser != null) {
            return AjaxResult.error("用户名[" + user.getUserName() + "]已存在");
        }

        Map<String, String> params = paramterService.getAllParamByMap();
        String defaultAgent = params.get("default_agent");
        // 查询代理信息
        if (StringUtils.isBlank(user.getUserAgent())) {
            user.setUserAgent(defaultAgent);
        } else {
            User agent = userService.getUserByName(user.getUserAgent());
            user.setUserAgent(agent == null ? defaultAgent : agent.getUserName());
        }

        Date now = new Date();
        User temp = new User();
        temp.setUserName(user.getUserName());
        temp.setBalance(new BigDecimal("0"));
        temp.setRealName(null);
        temp.setIdCard(null);
        temp.setLoginPwd(SecureUtil.md5(user.getLoginPwd()));
        temp.setPayPwd(SecureUtil.md5(user.getPayPwd()));
        temp.setInviteCode(RandomUtil.randomNumbers(6));
        temp.setWalletAddr(null);
        temp.setBankCardNum(null);
        temp.setBankName(null);
        temp.setBankAddr(null);
        temp.setUserStatus(0L);
        temp.setUserLevelId(0L);
        temp.setUserAgent(user.getUserAgent());
        temp.setIsRealName(1L);
        temp.setRegisterTime(now);
        temp.setRegisterIp("0.0.0.1");
        temp.setLastTime(null);
        temp.setLastIp(null);
        temp.setModifyTime(now);

        return toAjax(userService.insertUser(temp));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:user:edit')")
    @Log(title = "【用户修改】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody User user)
    {
        boolean matchLoginPwd = ReUtil.isMatch("^[a-zA-Z0-9]{6,12}$", user.getLoginPwd());
        if (!matchLoginPwd) {
            return AjaxResult.error("请输入6-12位登录密码");
        }
        boolean matchPayPwd = ReUtil.isMatch("^\\d{6}$", user.getPayPwd());
        if (!matchPayPwd) {
            return AjaxResult.error("请输入6位支付密码");
        }

        user.setLoginPwd(SecureUtil.md5(user.getLoginPwd()));
        user.setPayPwd(SecureUtil.md5(user.getPayPwd()));

        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:user:remove')")
    @Log(title = "【用户删除】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userService.deleteUserByIds(ids));
    }

    @Transactional
    @PreAuthorize("@ss.hasPermi('business:user:optMoney')")
    @Log(title = "【用户上下分】", businessType = BusinessType.UPDATE)
    @PostMapping("/optMoney")
    public AjaxResult optMoney(@RequestBody OptUserMoneyRequest request) throws Exception {
        if (StringUtils.isBlank(request.getRemark())) {
            return AjaxResult.error("请输入备注");
        }
        User user = userService.getUserByName(request.getUserName());
        if (user == null) {
            return AjaxResult.error("用户不存在.");
        }
        Date now = new Date();
        BigDecimal money = new BigDecimal(request.getMoney());
        if (StringUtils.equals("1", request.getType())) {
            // 加钱
            userService.updateUserBalance(user.getUserName(), money);
            // 充值记录
            String orderNo = IdUtil.getSnowflakeNextIdStr();
            Deposit deposit = new Deposit();
            deposit.setOrderNo(orderNo);
            deposit.setUserName(user.getUserName());
            deposit.setRealName(user.getRealName());
            deposit.setOptAmount(money);
            deposit.setBeforeAmount(user.getBalance());
            deposit.setAfterAmount(NumberUtil.add(user.getBalance(), money));
            deposit.setOptType(1L);
            deposit.setOptTime(now);
            deposit.setStatus(1L);
            deposit.setOptUser(getUsername());
            deposit.setUserAgent(user.getUserAgent());
            deposit.setRemark(request.getRemark());
            depositService.insertDeposit(deposit);

            // 流水记录
            Account account = new Account();
            account.setUserName(user.getUserName());
            account.setOptAmount(money);
            account.setBeforeAmount(user.getBalance());
            account.setAfterAmount(NumberUtil.add(user.getBalance(), money));
            account.setType(1L);
            account.setOptType(1L);
            account.setOptTime(new Date());
            account.setUserAgent(user.getUserAgent());
            account.setRefNo(orderNo);
            account.setAccountNo(IdUtil.getSnowflakeNextIdStr());
            account.setRemark(request.getRemark());
            accountService.insertAccount(account);
            return toAjax(true);
        } else if (StringUtils.equals("2", request.getType())) {
            if (money.doubleValue() > user.getBalance().doubleValue()) {
                return AjaxResult.error("用户账户余额为:" + user.getBalance() + "元");
            }
            // 加钱
            userService.updateUserBalance(user.getUserName(), money.negate());
            // 提现记录
            String orderNo = IdUtil.getSnowflakeNextIdStr();
            Withdraw withdraw = new Withdraw();
            withdraw.setOrderNo(orderNo);
            withdraw.setUserName(user.getUserName());
            withdraw.setOptAmount(money);
            withdraw.setBeforeAmount(user.getBalance());
            withdraw.setAfterAmount(NumberUtil.sub(user.getBalance(), money));
            withdraw.setWalletAddr(null);
            withdraw.setBankCardNum(null);
            withdraw.setBankName(null);
            withdraw.setBankAddr(null);
            withdraw.setOptType(0L);
            withdraw.setStatus(1L);
            withdraw.setOptTime(now);
            withdraw.setCheckTime(now);
            withdraw.setOperator(getUsername());
            withdraw.setUserAgent(user.getUserAgent());
            withdraw.setRealName(user.getRealName());
            withdraw.setRemark(request.getRemark());
            withdrawService.insertWithdraw(withdraw);

            // 流水记录
            Account account = new Account();
            account.setUserName(user.getUserName());
            account.setOptAmount(money);
            account.setBeforeAmount(user.getBalance());
            account.setAfterAmount(NumberUtil.sub(user.getBalance(), money));
            account.setType(2L);
            account.setOptType(2L);
            account.setOptTime(now);
            account.setUserAgent(user.getUserAgent());
            account.setRefNo(orderNo);
            account.setAccountNo(IdUtil.getSnowflakeNextIdStr());
            accountService.insertAccount(account);
            account.setRemark(request.getRemark());
            // 下分
            return toAjax(true);
        }
        return toAjax(false);
    }
}
