package com.juhai.business.service;

import java.util.List;
import java.util.Map;

import com.juhai.business.domain.Paramter;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public interface IParamterService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Paramter selectParamterById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param paramter 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Paramter> selectParamterList(Paramter paramter);

    /**
     * 新增【请填写功能名称】
     * 
     * @param paramter 【请填写功能名称】
     * @return 结果
     */
    public int insertParamter(Paramter paramter);

    /**
     * 修改【请填写功能名称】
     * 
     * @param paramter 【请填写功能名称】
     * @return 结果
     */
    public int updateParamter(Paramter paramter);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteParamterByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteParamterById(Long id);

    Map<String, String> getAllParamByMap();
}
