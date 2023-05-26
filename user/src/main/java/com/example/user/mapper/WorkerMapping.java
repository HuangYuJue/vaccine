package com.example.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Worker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkerMapping extends BaseMapper<Worker> {
}
