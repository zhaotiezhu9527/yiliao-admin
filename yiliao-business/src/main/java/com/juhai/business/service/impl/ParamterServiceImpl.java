package com.juhai.business.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollStreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.ParamterMapper;
import com.juhai.business.domain.Paramter;
import com.juhai.business.service.IParamterService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@Service
public class ParamterServiceImpl implements IParamterService 
{
    @Autowired
    private ParamterMapper paramterMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Paramter selectParamterById(Long id)
    {
        return paramterMapper.selectParamterById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param paramter 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Paramter> selectParamterList(Paramter paramter)
    {
        return paramterMapper.selectParamterList(paramter);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param paramter 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertParamter(Paramter paramter)
    {
        return paramterMapper.insertParamter(paramter);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param paramter 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateParamter(Paramter paramter)
    {
        return paramterMapper.updateParamter(paramter);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteParamterByIds(Long[] ids)
    {
        return paramterMapper.deleteParamterByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteParamterById(Long id)
    {
        return paramterMapper.deleteParamterById(id);
    }

    @Override
    public Map<String, String> getAllParamByMap() {
        List<Paramter> list = this.selectParamterList(null);
        return CollStreamUtil.toMap(list, Paramter::getParamKey, Paramter::getParamValue);
    }
}
