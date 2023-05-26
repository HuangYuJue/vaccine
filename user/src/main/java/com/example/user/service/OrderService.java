package com.example.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Order;
import com.example.vo.OrderVo;

import java.util.List;

public interface OrderService extends IService<Order> {
    //添加订单
    void add(Order order);

    //取消订单
    void cancel(int id);

    //根据token的id获取历史/现在/已取消订单
    IPage<Order> selectOld(Integer userId, Page<Order> pages, OrderVo orderVo);
}
