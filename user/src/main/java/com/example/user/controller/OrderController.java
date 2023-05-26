package com.example.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.client.AdminClient;
import com.example.model.Order;
import com.example.model.Result;
import com.example.user.help.JwtHelper;
import com.example.user.service.OrderService;
import com.example.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 点击疫苗预约后保存了疫苗id，弹出接种点选择，选择完接种点再选择工作人员
     */
    /**
     * 添加订单，根据用户id、疫苗id、工作人员id（工作人员id通过选择接种点后选择）
     * 先判断疫苗是否存在
     */
    @PostMapping("add")
    public Result add(@RequestBody Order order, HttpServletRequest request){
        String token = request.getHeader("token");
        Integer userId = JwtHelper.getUserId(token);
        //userId = 1;
        order.setUserId(userId);
        orderService.add(order);
        return Result.ok();
    }

    /**
     * 取消订单
     * 先判断是否接种，未接种可以取消，并将数量恢复
     */
    @GetMapping("cancel/{id}")
    public Result cancel(@PathVariable int id){
        orderService.cancel(id);
        return Result.ok();
    }

    /**
     * 条件分页查询历史订单
     * 查询历史订单时，可以查看
     * 疫苗名称、是否接种、是否取消
     */
    @PostMapping("selectOld/{limit}/{page}")
    public Result selectOld(@PathVariable int limit, @PathVariable int page,
                            @RequestBody OrderVo orderVo, HttpServletRequest request){
        String token = request.getHeader("token");
        Integer userId = JwtHelper.getUserId(token);
        Page<Order> pages = new Page<>(page, limit);
        IPage<Order> iPage = orderService.selectOld(userId, pages, orderVo);
        return Result.ok(iPage);
    }
}
