package com.example.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.Result;
import com.example.model.Vaccine;
import com.example.user.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    /**
     * 根据疫苗名字或类型名字模糊查找
     */
    @GetMapping("findByName/{vacname}")
    public Result findByName(@PathVariable String vacname){
        List<Vaccine> vaccineList = vaccineService.findByName(vacname);
        return Result.ok(vaccineList);
    }

    /**
     * 根据疫苗id查看详情
     */
    @GetMapping("findVaccine/{id}")
    public Result findVaccine(@PathVariable int id){
        Vaccine vaccine = vaccineService.getById(id);
        return Result.ok(vaccine);
    }

    /**
     * 根据疫苗类型以及疫苗名称获取疫苗（已上线：deleted=1）
     */
    @GetMapping("findVaccines/{limit}/{page}/{classifyId}")
    public Result findVaccines(@PathVariable int limit,
                              @PathVariable int page,
                              @PathVariable int classifyId,
                              String vacname){
        Page pages = new Page(page, limit);
        IPage<Vaccine> list = vaccineService.findVaccine(pages, classifyId, vacname);
        return Result.ok(list);
    }
}
