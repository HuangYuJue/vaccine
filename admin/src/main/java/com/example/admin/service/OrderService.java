package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Order;
import com.example.vo.OrderVo;

public interface OrderService extends IService<Order> {

    /**
     * 条件分页查询订单
     * 用户名、疫苗名称、工作人员名称、接种点名称、接种状态以及支付状态
     */
    IPage<Order> selectPage(Page<Order> pages, OrderVo orderVo);

    /**
     * 删除订单
     * 只可删除已接种或已取消的订单
     */
    void removeById(int id);

    /**
     * 设为接种状态
     */
    void vacstatus(int id);

    /**
     * 查看详情
     */
    Order getOrder(int id);
}
