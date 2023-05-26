package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.service.ClassifyService;
import com.example.model.Classify;
import com.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin/classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    /**
     * 添加疫苗类型
     */
    @PostMapping("add")
    public Result add(@RequestBody Classify classify){
        classifyService.insert(classify);
        return Result.ok();
    }

    /**
     * 分页查询所有疫苗类型
     */
    @GetMapping("select/{limit}/{page}")
    public Result select(@PathVariable int limit, @PathVariable int page, Classify classify){
        Page<Classify> pages = new Page<>(page,limit);
        IPage<Classify> iPage = classifyService.selectPage(pages,classify);
        return Result.ok(iPage);
    }


    /**
     * 修改
     */
    @PostMapping("update")
    private Result update(@RequestBody Classify classify){
        classifyService.update(classify);
        return Result.ok();
    }

    /**
     * 删除（逻辑删除）
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        classifyService.delete(id);
        return Result.ok();
    }

    /**
     *取消删除
     */
    @GetMapping("cancel/{id}")
    public Result cancel(@PathVariable int id){
        classifyService.cancel(id);
        return Result.ok();
    }

    @GetMapping("getById/{id}")
    public Result getById(@PathVariable int id){
        Classify byId = classifyService.getById(id);
        return Result.ok(byId);
    }
}
