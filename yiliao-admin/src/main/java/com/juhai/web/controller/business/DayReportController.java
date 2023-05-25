package com.juhai.web.controller.business;

import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.juhai.business.domain.*;
import com.juhai.business.service.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 日报表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/business/dayReport")
public class DayReportController extends BaseController
{
    @Autowired
    private IDayReportService dayReportService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private IOrderService orderService;

    /**
     * 查询日报表列表
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(DayReport dayReport)
    {
        startPage();
        List<DayReport> list = dayReportService.selectDayReportList(dayReport);
        return getDataTable(list);
    }

    /**
     * 今日总报表
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:allReport')")
    @GetMapping("/allReport")
    public AjaxResult today()
    {
        Map<String, Object> response = new HashMap<>();

        Date now = new Date();
        String beginTime = DateUtil.formatDateTime(DateUtil.beginOfDay(now));
        String endTime = DateUtil.formatDateTime(DateUtil.endOfDay(now));

        JSONObject today = new JSONObject();

        // 查询今日注册量
        User user = new User();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("beginTime", beginTime);
        userMap.put("endTime", endTime);
        user.setParams(userMap);
        List<User> users = userService.selectUserList(user);
        today.put("registerCount", users.size());

        // 今日充值、人数
        Deposit deposit = new Deposit();
        Map<String, Object> depositMap = new HashMap<>();
        depositMap.put("beginTime", beginTime);
        depositMap.put("endTime", endTime);
        deposit.setParams(depositMap);
        List<Deposit> deposits = depositService.selectDepositList(deposit);
        BigDecimal depositAmount = new BigDecimal(0);
        Set<String> depositSet = new HashSet<>();
        for (Deposit temp : deposits) {
            depositAmount = NumberUtil.add(depositAmount, temp.getOptAmount());
            depositSet.add(temp.getUserName());
        }
        today.put("depositCount", depositSet.size());
        today.put("depositAmount", depositAmount);

        // 今日提现、人数
        Withdraw withdraw = new Withdraw();
        Map<String, Object> withdrawMap = new HashMap<>();
        withdrawMap.put("beginTime", beginTime);
        withdrawMap.put("endTime", endTime);
        withdraw.setParams(withdrawMap);
        List<Withdraw> withdraws = withdrawService.selectWithdrawList(withdraw);
        BigDecimal withdrawAmount = new BigDecimal(0);
        Set<String> withdrawSet = new HashSet<>();
        for (Withdraw temp : withdraws) {
            withdrawAmount = NumberUtil.add(withdrawAmount, temp.getOptAmount());
            withdrawSet.add(temp.getUserName());
        }
        today.put("withdrawCount", withdrawSet.size());
        today.put("withdrawAmount", withdrawAmount);

        // 今日投资、人数
        Order order = new Order();
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("beginTime", beginTime);
        orderMap.put("endTime", endTime);
        order.setParams(orderMap);
        List<Order> orders = orderService.selectOrderList(order);
        // 总投资
        BigDecimal investmentAmount = new BigDecimal(0);
        // 已返还利息
        BigDecimal returnIncome = new BigDecimal(0);
        // 待返还利息
        BigDecimal waitReturnIncome = new BigDecimal(0);
        Set<String> investmentSet = new HashSet<>();
        for (Order temp : orders) {
            investmentAmount = NumberUtil.add(investmentAmount, temp.getAmount());
            investmentSet.add(temp.getUserName());
            if (temp.getStatus().intValue() == 0) {
                // 待返款
                waitReturnIncome = NumberUtil.add(waitReturnIncome, temp.getForecastReturnAmount());
            } else if (temp.getStatus().intValue() == 1) {
                // 已返款
                returnIncome = NumberUtil.add(returnIncome, temp.getForecastReturnAmount());
            }
        }
        today.put("investmentCount", investmentSet.size());
        today.put("investmentAmount", investmentAmount);
        today.put("waitReturnIncome", waitReturnIncome);
        today.put("returnIncome", returnIncome);
        // 今日返款

        response.put("today", today);


        DayReport temp = new DayReport();
        temp.setToday(DateUtil.format(new Date(), "yyyyMMdd"));
        temp.setDepositAmount(depositAmount);
        temp.setWithdrawAmount(withdrawAmount);
        temp.setInvestmentAmount(investmentAmount);
        temp.setIncomeAmount(NumberUtil.add(waitReturnIncome, returnIncome));
        temp.setRegisterCount(Long.valueOf(users.size()));
        temp.setDepositCount(0L);
        temp.setWithdrawCount(0L);
        temp.setInvestmentCount(0L);

        List<DayReport> dayReports = dayReportService.selectHistoryList();
        if (CollUtil.isNotEmpty(dayReports)) {
            Collections.reverse(dayReports);
        }
        dayReports.add(temp);
        response.put("history", dayReports);
        return AjaxResult.success(response);
    }

    /**
     * 导出日报表列表
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:export')")
    @Log(title = "日报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DayReport dayReport)
    {
        List<DayReport> list = dayReportService.selectDayReportList(dayReport);
        ExcelUtil<DayReport> util = new ExcelUtil<DayReport>(DayReport.class);
        util.exportExcel(response, list, "日报表数据");
    }

    /**
     * 获取日报表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dayReportService.selectDayReportById(id));
    }

    /**
     * 新增日报表
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:add')")
    @Log(title = "日报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DayReport dayReport)
    {
        return toAjax(dayReportService.insertDayReport(dayReport));
    }

    /**
     * 修改日报表
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:edit')")
    @Log(title = "日报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DayReport dayReport)
    {
        return toAjax(dayReportService.updateDayReport(dayReport));
    }

    /**
     * 删除日报表
     */
    @PreAuthorize("@ss.hasPermi('business:dayReport:remove')")
    @Log(title = "日报表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dayReportService.deleteDayReportByIds(ids));
    }
}
