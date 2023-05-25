package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.UserReport;

/**
 * 用户日报表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
public interface IUserReportService 
{
    /**
     * 查询用户日报表
     * 
     * @param id 用户日报表主键
     * @return 用户日报表
     */
    public UserReport selectUserReportById(Long id);

    /**
     * 查询用户日报表列表
     * 
     * @param userReport 用户日报表
     * @return 用户日报表集合
     */
    public List<UserReport> selectUserReportList(UserReport userReport);

    /**
     * 新增用户日报表
     * 
     * @param userReport 用户日报表
     * @return 结果
     */
    public int insertUserReport(UserReport userReport);

    /**
     * 修改用户日报表
     * 
     * @param userReport 用户日报表
     * @return 结果
     */
    public int updateUserReport(UserReport userReport);

    /**
     * 批量删除用户日报表
     * 
     * @param ids 需要删除的用户日报表主键集合
     * @return 结果
     */
    public int deleteUserReportByIds(Long[] ids);

    /**
     * 删除用户日报表信息
     * 
     * @param id 用户日报表主键
     * @return 结果
     */
    public int deleteUserReportById(Long id);

    void insertOrUpdate(UserReport report) throws Exception;
}
