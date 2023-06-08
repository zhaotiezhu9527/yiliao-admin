package com.juhai.web.controller.business;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson2.JSONObject;
import com.juhai.business.domain.Account;
import com.juhai.business.domain.User;
import com.juhai.business.domain.UserReport;
import com.juhai.business.domain.Withdraw;
import com.juhai.business.service.IAccountService;
import com.juhai.business.service.IUserReportService;
import com.juhai.business.service.IUserService;
import com.juhai.business.service.IWithdrawService;
import com.juhai.common.annotation.Anonymous;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.core.page.TableDataInfo;
import com.juhai.common.enums.BusinessType;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.web.controller.business.request.WithdrawCheckRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/business/withdraw")
public class WithdrawController extends BaseController
{
    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserReportService userReportService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:list')")
    @GetMapping("/list")
    public TableDataInfo list(Withdraw withdraw)
    {
        startPage();
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
        TableDataInfo table = getDataTable(list);

        // 报表数据
        List<Withdraw> withdraws = withdrawService.selectWithdrawList(withdraw);
        BigDecimal success = new BigDecimal(0);
        BigDecimal fail = new BigDecimal(0);
        BigDecimal wait = new BigDecimal(0);
        for (Withdraw temp : withdraws) {
            if (temp.getStatus().intValue() == 1) {
                success = NumberUtil.add(success, temp.getOptAmount());
            } else if (temp.getStatus().intValue() == 0) {
                wait = NumberUtil.add(wait, temp.getOptAmount());
            } else if (temp.getStatus().intValue() == 2) {
                fail = NumberUtil.add(fail, temp.getOptAmount());
            }
        }
        JSONObject other = new JSONObject();
        other.put("success", success);
        other.put("fail", fail);
        other.put("wait", wait);

        table.setOther(other);
        return table;
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Withdraw withdraw)
    {
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
        ExcelUtil<Withdraw> util = new ExcelUtil<Withdraw>(Withdraw.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(withdrawService.selectWithdrawById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Withdraw withdraw)
    {
        return toAjax(withdrawService.insertWithdraw(withdraw));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Withdraw withdraw)
    {
        return toAjax(withdrawService.updateWithdraw(withdraw));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(withdrawService.deleteWithdrawByIds(ids));
    }

    /**
     * 审核【请填写功能名称】
     */
    @Transactional
    @PreAuthorize("@ss.hasPermi('business:withdraw:check')")
    @Log(title = "提现审核", businessType = BusinessType.UPDATE)
    @PostMapping("/check")
    public AjaxResult check(@RequestBody WithdrawCheckRequest request) throws Exception {

        Withdraw withdraw = withdrawService.selectWithdrawById(NumberUtils.toLong(request.getId()));
        if (withdraw.getStatus().intValue() != 0) {
            return AjaxResult.error("该订单已被审核.");
        }
        if (StringUtils.equals(request.getStatus(), "1")) {
            // 通过
            Withdraw temp = new Withdraw();
            temp.setId(withdraw.getId());
            temp.setStatus(1L);
            temp.setRemark(request.getRemark());
            temp.setOperator(getUsername());
            temp.setCheckTime(new Date());
            withdrawService.updateWithdraw(temp);

            // 记录报表
            UserReport report = new UserReport();
            report.setUserName(withdraw.getUserName());
            report.setToday(DateUtil.format(withdraw.getOptTime(), "yyyyMMdd"));
            report.setDepositAmount(new BigDecimal("0"));
            report.setWithdrawAmount(withdraw.getOptAmount());
            report.setInvestmentAmount(new BigDecimal("0"));
            report.setIncomeAmount(new BigDecimal("0"));
            userReportService.insertOrUpdate(report);
        } else {
            // 拒绝
            Withdraw temp = new Withdraw();
            temp.setId(withdraw.getId());
            temp.setStatus(2L);
            temp.setRemark(request.getRemark());
            temp.setOperator(getUsername());
            temp.setCheckTime(new Date());
            withdrawService.updateWithdraw(temp);

            User user = userService.getUserByName(withdraw.getUserName());

            // 返还金额
            userService.updateUserBalance(withdraw.getUserName(), withdraw.getOptAmount());

            // 记录流水
            Account account = new Account();
            account.setUserName(withdraw.getUserName());
            account.setOptAmount(withdraw.getOptAmount());
            account.setBeforeAmount(user.getBalance());
            account.setAfterAmount(NumberUtil.add(user.getBalance(), withdraw.getOptAmount()));
            account.setType(1L);
            account.setOptType(1L);
            account.setOptTime(new Date());
            account.setUserAgent(user.getUserAgent());
            account.setRefNo(withdraw.getOrderNo());
            account.setAccountNo(IdUtil.getSnowflakeNextIdStr());
            account.setRemark("提现退还金额:" + withdraw.getOptAmount() + "元");
            accountService.insertAccount(account);
        }
        return toAjax(true);
    }

    @Anonymous
    @GetMapping(value = "/getNotice")
    public AjaxResult getNotice()
    {

        Withdraw withdraw = new Withdraw();
        withdraw.setStatus(0L);
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
//        String key = "user:withdraw:notice";
//        Boolean flag = redisTemplate.hasKey(key);
//        redisTemplate.delete(key);
        return success(CollUtil.isNotEmpty(list));
    }
}
