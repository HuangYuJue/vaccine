package com.example.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Vaccine;

import java.util.List;

public interface VaccineService extends IService<Vaccine> {

    //根据名字模糊查找
    List<Vaccine> findByName(String vacname);

    //根据疫苗类型id以及疫苗名称查找疫苗
    IPage<Vaccine> findVaccine(Page pages, int classifyId, String vacname);
}
