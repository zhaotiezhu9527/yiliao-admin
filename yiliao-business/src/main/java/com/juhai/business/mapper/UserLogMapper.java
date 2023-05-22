package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.UserLog;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public interface UserLogMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public UserLog selectUserLogById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param userLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<UserLog> selectUserLogList(UserLog userLog);

    /**
     * 新增【请填写功能名称】
     * 
     * @param userLog 【请填写功能名称】
     * @return 结果
     */
    public int insertUserLog(UserLog userLog);

    /**
     * 修改【请填写功能名称】
     * 
     * @param userLog 【请填写功能名称】
     * @return 结果
     */
    public int updateUserLog(UserLog userLog);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteUserLogById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserLogByIds(Long[] ids);
}
