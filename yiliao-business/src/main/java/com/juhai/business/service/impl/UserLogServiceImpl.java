package com.juhai.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.UserLogMapper;
import com.juhai.business.domain.UserLog;
import com.juhai.business.service.IUserLogService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@Service
public class UserLogServiceImpl implements IUserLogService 
{
    @Autowired
    private UserLogMapper userLogMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public UserLog selectUserLogById(Long id)
    {
        return userLogMapper.selectUserLogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param userLog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<UserLog> selectUserLogList(UserLog userLog)
    {
        return userLogMapper.selectUserLogList(userLog);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param userLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertUserLog(UserLog userLog)
    {
        return userLogMapper.insertUserLog(userLog);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param userLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateUserLog(UserLog userLog)
    {
        return userLogMapper.updateUserLog(userLog);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteUserLogByIds(Long[] ids)
    {
        return userLogMapper.deleteUserLogByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteUserLogById(Long id)
    {
        return userLogMapper.deleteUserLogById(id);
    }
}
