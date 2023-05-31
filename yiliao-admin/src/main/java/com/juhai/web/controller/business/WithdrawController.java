package com.juhai.web.controller.business;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson2.JSONObject;
import com.juhai.business.domain.Withdraw;
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
    private StringRedisTemplate redisTemplate;

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
    @PreAuthorize("@ss.hasPermi('business:withdraw:check')")
    @Log(title = "提现审核", businessType = BusinessType.UPDATE)
    @PostMapping("/check")
    public AjaxResult check(@RequestBody WithdrawCheckRequest request)
    {

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
        } else {
            // 拒绝
            Withdraw temp = new Withdraw();
            temp.setId(withdraw.getId());
            temp.setStatus(2L);
            temp.setRemark(request.getRemark());
            temp.setOperator(getUsername());
            temp.setCheckTime(new Date());
            withdrawService.updateWithdraw(temp);
        }
        return toAjax(true);
    }

    @Anonymous
    @GetMapping(value = "/getNotice")
    public AjaxResult getNotice()
    {
        String key = "user:withdraw:notice";
        Boolean flag = redisTemplate.hasKey(key);
        redisTemplate.delete(key);
        return success(flag);
    }
}
