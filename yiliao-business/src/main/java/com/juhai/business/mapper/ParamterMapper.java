package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Paramter;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public interface ParamterMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteParamterById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteParamterByIds(Long[] ids);
}
