package com.juhai.web.controller.business;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson2.JSONObject;
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
import com.juhai.business.domain.Deposit;
import com.juhai.business.service.IDepositService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;
import springfox.documentation.spring.web.json.Json;

/**
 * 【请填写功能名称】Controller
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/business/deposit")
public class DepositController extends BaseController
{
    @Autowired
    private IDepositService depositService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:list')")
    @GetMapping("/list")
    public TableDataInfo list(Deposit deposit)
    {
        // 分页列表数据
        startPage();
        List<Deposit> list = depositService.selectDepositList(deposit);
        TableDataInfo table = getDataTable(list);

        // 报表数据
        List<Deposit> deposits = depositService.selectDepositList(deposit);
        BigDecimal success = new BigDecimal(0);
        BigDecimal fail = new BigDecimal(0);
        BigDecimal wait = new BigDecimal(0);
        for (Deposit temp : deposits) {
            if (temp.getStatus().intValue() == 1) {
                success = NumberUtil.add(success, temp.getOptAmount());
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
    @PreAuthorize("@ss.hasPermi('business:deposit:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Deposit deposit)
    {
        List<Deposit> list = depositService.selectDepositList(deposit);
        ExcelUtil<Deposit> util = new ExcelUtil<Deposit>(Deposit.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(depositService.selectDepositById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Deposit deposit)
    {
        return toAjax(depositService.insertDeposit(deposit));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Deposit deposit)
    {
        return toAjax(depositService.updateDeposit(deposit));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(depositService.deleteDepositByIds(ids));
    }
}
