package com.juhai.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.UserReportMapper;
import com.juhai.business.domain.UserReport;
import com.juhai.business.service.IUserReportService;

/**
 * 用户日报表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-24
 */
@Service
public class UserReportServiceImpl implements IUserReportService 
{
    @Autowired
    private UserReportMapper userReportMapper;

    /**
     * 查询用户日报表
     * 
     * @param id 用户日报表主键
     * @return 用户日报表
     */
    @Override
    public UserReport selectUserReportById(Long id)
    {
        return userReportMapper.selectUserReportById(id);
    }

    /**
     * 查询用户日报表列表
     * 
     * @param userReport 用户日报表
     * @return 用户日报表
     */
    @Override
    public List<UserReport> selectUserReportList(UserReport userReport)
    {
        return userReportMapper.selectUserReportList(userReport);
    }

    /**
     * 新增用户日报表
     * 
     * @param userReport 用户日报表
     * @return 结果
     */
    @Override
    public int insertUserReport(UserReport userReport)
    {
        return userReportMapper.insertUserReport(userReport);
    }

    /**
     * 修改用户日报表
     * 
     * @param userReport 用户日报表
     * @return 结果
     */
    @Override
    public int updateUserReport(UserReport userReport)
    {
        return userReportMapper.updateUserReport(userReport);
    }

    /**
     * 批量删除用户日报表
     * 
     * @param ids 需要删除的用户日报表主键
     * @return 结果
     */
    @Override
    public int deleteUserReportByIds(Long[] ids)
    {
        return userReportMapper.deleteUserReportByIds(ids);
    }

    /**
     * 删除用户日报表信息
     * 
     * @param id 用户日报表主键
     * @return 结果
     */
    @Override
    public int deleteUserReportById(Long id)
    {
        return userReportMapper.deleteUserReportById(id);
    }

    @Override
    public void insertOrUpdate(UserReport report) throws Exception {
        int i = userReportMapper.insertOrUpdate(report);
        if (i <= 0) {
            throw new Exception("修改日报表失败");
        }
    }
}
