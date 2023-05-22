package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.Order;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-22
 */
public interface IOrderService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Order selectOrderById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param order 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增【请填写功能名称】
     * 
     * @param order 【请填写功能名称】
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改【请填写功能名称】
     * 
     * @param order 【请填写功能名称】
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteOrderById(Long id);
}
