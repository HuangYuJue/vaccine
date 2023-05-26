package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Classify;

import java.util.List;

public interface ClassifyService extends IService<Classify> {

    //根据名字模糊查找
    List<Classify> findByName(String claname);
}
