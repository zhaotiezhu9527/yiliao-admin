package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.DayReport;

/**
 * 日报表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
public interface DayReportMapper 
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
     * 删除日报表
     * 
     * @param id 日报表主键
     * @return 结果
     */
    public int deleteDayReportById(Long id);

    /**
     * 批量删除日报表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDayReportByIds(Long[] ids);

    /**
     * 查询前十天的数据
     * @return
     */
    public List<DayReport> selectHistoryList();
}
