package com.example.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapping extends BaseMapper<Order> {
}