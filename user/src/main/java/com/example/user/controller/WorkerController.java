package com.example.user.controller;

import com.example.model.Result;
import com.example.model.Worker;
import com.example.user.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @GetMapping("findWorkers/{addressId}")
    public Result findWorkers(@PathVariable int addressId){
        List<Worker> list = workerService.findWorkers(addressId);
        return Result.ok(list);
    }
}
