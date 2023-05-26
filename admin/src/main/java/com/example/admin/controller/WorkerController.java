package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.service.WorkerService;
import com.example.model.Result;
import com.example.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    /**
     * 根据接种点获取工作人员分页查询
     */
    @PostMapping("select/{limit}/{page}")
    public Result select(@PathVariable int limit,
                         @PathVariable int page,
                         @RequestBody Worker worker){
        Page<Worker> pages = new Page<>(page, limit);
        IPage<Worker> iPage = workerService.select(pages, worker);
        return Result.ok(iPage);
    }

    /**
     * 添加工作人员
     */
    @PostMapping("add")
    public Result add(@RequestBody Worker worker){
        workerService.insert(worker);
        return Result.ok();
    }

    /**
     * 修改
     */
    @PostMapping("update")
    public Result update(@RequestBody Worker worker){
        workerService.update(worker);
        return Result.ok();
    }

    /**
     * 删除
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        workerService.delete(id);
        return Result.ok();
    }

    /**
     * 根据id获取
     */
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable int id){
        Worker worker = workerService.getById(id);
        return Result.ok(worker);
    }

}
