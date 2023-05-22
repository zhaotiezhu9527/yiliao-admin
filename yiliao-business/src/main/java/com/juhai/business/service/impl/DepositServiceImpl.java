package com.juhai.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.DepositMapper;
import com.juhai.business.domain.Deposit;
import com.juhai.business.service.IDepositService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@Service
public class DepositServiceImpl implements IDepositService 
{
    @Autowired
    private DepositMapper depositMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Deposit selectDepositById(Long id)
    {
        return depositMapper.selectDepositById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deposit 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Deposit> selectDepositList(Deposit deposit)
    {
        return depositMapper.selectDepositList(deposit);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param deposit 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDeposit(Deposit deposit)
    {
        return depositMapper.insertDeposit(deposit);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param deposit 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDeposit(Deposit deposit)
    {
        return depositMapper.updateDeposit(deposit);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDepositByIds(Long[] ids)
    {
        return depositMapper.deleteDepositByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDepositById(Long id)
    {
        return depositMapper.deleteDepositById(id);
    }
}
