package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.model.Worker;
import com.example.user.mapper.WorkerMapping;
import com.example.user.service.WorkerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapping, Worker> implements WorkerService {
    @Override
    public List<Worker> findWorkers(int addressId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("address", addressId);
        List list = baseMapper.selectList(wrapper);
        return list;
    }
}
