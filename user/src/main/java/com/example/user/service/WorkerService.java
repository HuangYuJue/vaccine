package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Worker;

import java.util.List;

public interface WorkerService extends IService<Worker> {
    List<Worker> findWorkers(int addressId);
}
