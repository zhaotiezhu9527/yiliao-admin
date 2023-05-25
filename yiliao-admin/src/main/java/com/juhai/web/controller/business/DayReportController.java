package com.juhai.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.juhai.business.domain.DayReport;
import com.juhai.business.service.IDayReportService;
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
