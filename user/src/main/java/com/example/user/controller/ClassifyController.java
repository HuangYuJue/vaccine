package com.example.user.controller;

import com.example.model.Classify;
import com.example.model.Result;
import com.example.user.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    /**
     * 根据classify名字模糊查找
     */
    @GetMapping("findByName/{claname}")
    public Result findByName(@PathVariable String claname){
        List<Classify> classifyList = classifyService.findByName(claname);
        return Result.ok(classifyList);
    }

    /**
     * 根据classify的id获取classify的详情
     */
    @GetMapping("getClassify/{id}")
    public Result getClassify(@PathVariable int id){
        Classify classify = classifyService.getById(id);
        return Result.ok(classify);
    }
}
