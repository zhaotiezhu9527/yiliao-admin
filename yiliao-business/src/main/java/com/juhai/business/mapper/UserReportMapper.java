package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.UserReport;
import org.apache.ibatis.annotations.Param;

/**
 * 用户日报表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
public interface UserReportMapper 
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
     * 删除用户日报表
     * 
     * @param id 用户日报表主键
     * @return 结果
     */
    public int deleteUserReportById(Long id);

    /**
     * 批量删除用户日报表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserReportByIds(Long[] ids);

    int insertOrUpdate(@Param("report") UserReport report);
}
