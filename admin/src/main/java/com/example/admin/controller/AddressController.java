package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.service.AddressService;
import com.example.model.Address;
import com.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 添加接种点
     */
    @PostMapping("add")
    public Result add(@RequestBody Address address){
        addressService.add(address);
        return Result.ok();
    }

    /**
     * 逻辑删除（下线）
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        addressService.delete(id);
        return Result.ok();
    }

    /**
     * 分页查询
     */
    @GetMapping("find/{limit}/{page}")
    public Result find(@PathVariable int limit,@PathVariable int page,
                       Address address){
        Page<Address> pages = new Page<>(page, limit);
        IPage<Address> iPage = addressService.selectPage(pages, address);
        return Result.ok(iPage);
    }

    /**
     * 修改
     */
    @PostMapping("update")
    public Result update(@RequestBody Address address){
        addressService.update(address);
        return Result.ok();
    }

    /**
     * 取消删除（取消下线）
     */
    @GetMapping("cancel/{id}")
    public Result cancel(@PathVariable int id){
        addressService.cancel(id);
        return Result.ok();
    }

    /**
     * 根据id获取地点
     */
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable int id){
        Address byId = addressService.getById(id);
        return Result.ok(byId);
    }
}
