package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.service.OrderService;
import com.example.model.Order;
import com.example.model.Result;
import com.example.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 条件分页查询订单
     * 用户名、疫苗名称、工作人员名称、接种点名称、接种状态以及是否取消
     */
    @PostMapping("selectPage/{limit}/{page}")
    public Result selectPage(@PathVariable int limit, @PathVariable int page,
                             @RequestBody(required = false) OrderVo orderVo){
        Page pages = new Page(page, limit);
        IPage<Order> iPage = orderService.selectPage(pages, orderVo);
        return Result.ok(iPage);
    }

    /**
     * 删除订单
     * 只可删除已接种或已取消的订单
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        orderService.removeById(id);
        return Result.ok();
    }

    /**
     * 设为接种状态
     * 已取消状态不能设置
     */
    @GetMapping("vacstatus/{id}")
    public Result vacstatus(@PathVariable int id){
        orderService.vacstatus(id);
        return Result.ok();
    }

    /**
     * 查看详情
     */
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable int id){
        Order order = orderService.getOrder(id);
        return Result.ok(order);
    }
}