package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.DayReport;

/**
 * 日报表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
public interface IDayReportService 
{
    /**
     * 查询日报表
     * 
     * @param id 日报表主键
     * @return 日报表
     */
    public DayReport selectDayReportById(Long id);

    /**
     * 查询日报表列表
     * 
     * @param dayReport 日报表
     * @return 日报表集合
     */
    public List<DayReport> selectDayReportList(DayReport dayReport);

    /**
     * 新增日报表
     * 
     * @param dayReport 日报表
     * @return 结果
     */
    public int insertDayReport(DayReport dayReport);

    /**
     * 修改日报表
     * 
     * @param dayReport 日报表
     * @return 结果
     */
    public int updateDayReport(DayReport dayReport);

    /**
     * 批量删除日报表
     * 
     * @param ids 需要删除的日报表主键集合
     * @return 结果
     */
    public int deleteDayReportByIds(Long[] ids);

    /**
     * 删除日报表信息
     * 
     * @param id 日报表主键
     * @return 结果
     */
    public int deleteDayReportById(Long id);
}
