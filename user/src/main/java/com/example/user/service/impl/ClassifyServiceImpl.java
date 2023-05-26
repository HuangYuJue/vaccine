package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.model.Classify;
import com.example.user.mapper.ClassifyMapping;
import com.example.user.service.ClassifyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapping, Classify> implements ClassifyService {
    @Override
    public List<Classify> findByName(String claname) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("claname", claname);
        List list = baseMapper.selectList(wrapper);
        return list;
    }
}