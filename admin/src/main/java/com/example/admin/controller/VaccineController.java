package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.service.VaccineService;
import com.example.model.Result;
import com.example.model.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/vaccine")
@CrossOrigin
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    /**
     * 插入疫苗数据
     */
    @PostMapping("add")
    public Result add(@RequestBody Vaccine vaccine){
        vaccineService.insert(vaccine);
        return Result.ok();
    }

    /**
     * 条件分页查询所有疫苗
     */
    @GetMapping("select/{limit}/{page}")
    public Result select(@PathVariable int limit,
                         @PathVariable int page,
                         Vaccine vaccine){
        Page<Vaccine> pages = new Page<>(page,limit);
        IPage<Vaccine> iPage = vaccineService.selectPage(pages, vaccine);
        return Result.ok(iPage);
    }

    /**
     * 修改疫苗数据
     */
    @PostMapping("update")
    public Result update(@RequestBody Vaccine vaccine){
        vaccineService.update(vaccine);
        return Result.ok();
    }

    /**
     * 逻辑删除疫苗
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        vaccineService.remove(id);
        return Result.ok();
    }
    /**
     * 取消删除
     */
    @GetMapping("cancel/{id}")
    public Result cancel(@PathVariable int id){
        vaccineService.cancel(id);
        return Result.ok();
    }

    /**
     * 根据id获取到疫苗信息
     */
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable int id){
        Vaccine vaccine = vaccineService.getById(id);
        System.out.println("vaccine:"+vaccine);
        return Result.ok(vaccine);
    }
}
