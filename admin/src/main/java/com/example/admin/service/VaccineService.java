package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Vaccine;

import java.util.List;

public interface VaccineService extends IService<Vaccine> {
    //条件分页查询
    IPage<Vaccine> selectPage(Page<Vaccine> pages, Vaccine vaccine);

    //插入
    void insert(Vaccine vaccine);

    //修改
    boolean update(Vaccine vaccine);

    //删除
    void remove(int id);

    //根据疫苗名称获取疫苗id
    List<Integer> getVaccineByName(String vacname);

    //取消删除
    void cancel(int id);

    //根据id获取
    Vaccine getById(int id);
}
