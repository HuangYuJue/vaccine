package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Worker;

import java.util.List;

public interface WorkerService extends IService<Worker> {

    //添加工作人员
    void insert(Worker worker);

    //修改工作人员信息
    void update(Worker worker);

    //删除工作人员
    void delete(int id);

    //根据工作人员名称获取工作人员
    List<Worker> getWorker(String workerName);

    //根据接种点id获取工作人员
    IPage<Worker> select(Page<Worker> pages, Worker worker);

    //根据接种点ids获取工作人员id
    List<Integer> getByIds(List<Integer> aids);

    //根据id获取工作人员
    Worker getById(int id);
}
