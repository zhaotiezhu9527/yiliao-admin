package com.juhai.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.WithdrawMapper;
import com.juhai.business.domain.Withdraw;
import com.juhai.business.service.IWithdrawService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@Service
public class WithdrawServiceImpl implements IWithdrawService 
{
    @Autowired
    private WithdrawMapper withdrawMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Withdraw selectWithdrawById(Long id)
    {
        return withdrawMapper.selectWithdrawById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param withdraw 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Withdraw> selectWithdrawList(Withdraw withdraw)
    {
        return withdrawMapper.selectWithdrawList(withdraw);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param withdraw 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertWithdraw(Withdraw withdraw)
    {
        return withdrawMapper.insertWithdraw(withdraw);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param withdraw 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateWithdraw(Withdraw withdraw)
    {
        return withdrawMapper.updateWithdraw(withdraw);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawByIds(Long[] ids)
    {
        return withdrawMapper.deleteWithdrawByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawById(Long id)
    {
        return withdrawMapper.deleteWithdrawById(id);
    }
}
