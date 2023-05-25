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
import com.juhai.business.domain.UserReport;
import com.juhai.business.service.IUserReportService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 用户日报表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/business/userReport")
public class UserReportController extends BaseController
{
    @Autowired
    private IUserReportService userReportService;

    /**
     * 查询用户日报表列表
     */
    @PreAuthorize("@ss.hasPermi('business:userReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserReport userReport)
    {
        startPage();
        List<UserReport> list = userReportService.selectUserReportList(userReport);
        return getDataTable(list);
    }

    /**
     * 导出用户日报表列表
     */
    @PreAuthorize("@ss.hasPermi('business:userReport:export')")
    @Log(title = "用户日报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserReport userReport)
    {
        List<UserReport> list = userReportService.selectUserReportList(userReport);
        ExcelUtil<UserReport> util = new ExcelUtil<UserReport>(UserReport.class);
        util.exportExcel(response, list, "用户日报表数据");
    }

    /**
     * 获取用户日报表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:userReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userReportService.selectUserReportById(id));
    }

    /**
     * 新增用户日报表
     */
    @PreAuthorize("@ss.hasPermi('business:userReport:add')")
    @Log(title = "用户日报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserReport userReport)
    {
        return toAjax(userReportService.insertUserReport(userReport));
    }

    /**
     * 修改用户日报表
     */
    @PreAuthorize("@ss.hasPermi('business:userReport:edit')")
    @Log(title = "用户日报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserReport userReport)
    {
        return toAjax(userReportService.updateUserReport(userReport));
    }

    /**
     * 删除用户日报表
     */
    @PreAuthorize("@ss.hasPermi('business:userReport:remove')")
    @Log(title = "用户日报表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userReportService.deleteUserReportByIds(ids));
    }
}
