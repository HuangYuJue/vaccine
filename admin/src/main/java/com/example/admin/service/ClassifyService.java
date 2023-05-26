package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Classify;

public interface ClassifyService extends IService<Classify> {
    //分页查询疫苗类型
    IPage<Classify> selectPage(Page<Classify> pages, Classify classify);

    //添加疫苗类型
    void insert(Classify classify);

    //逻辑删除
    void delete(int id);

    //修改
    void update(Classify classify);

    //取消删除
    void cancel(int id);
}
