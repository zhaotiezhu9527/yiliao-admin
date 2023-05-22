package com.juhai.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.AccountMapper;
import com.juhai.business.domain.Account;
import com.juhai.business.service.IAccountService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@Service
public class AccountServiceImpl implements IAccountService 
{
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Account selectAccountById(Long id)
    {
        return accountMapper.selectAccountById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param account 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Account> selectAccountList(Account account)
    {
        return accountMapper.selectAccountList(account);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param account 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAccount(Account account)
    {
        return accountMapper.insertAccount(account);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param account 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAccount(Account account)
    {
        return accountMapper.updateAccount(account);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAccountByIds(Long[] ids)
    {
        return accountMapper.deleteAccountByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAccountById(Long id)
    {
        return accountMapper.deleteAccountById(id);
    }
}
