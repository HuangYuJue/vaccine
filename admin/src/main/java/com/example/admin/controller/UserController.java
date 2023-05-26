package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.service.AdminService;
import com.example.admin.service.UserService;
import com.example.model.Admin;
import com.example.model.Result;
import com.example.model.User;
import com.example.vo.LoginPwVo;
import com.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginPwVo loginPwVo){
        Map<String, Object> login = adminService.login(loginPwVo);
        if (login != null){
            return Result.ok(login);
        }
        return Result.fail();
    }

    /**
     * 分页查询所有用户/模糊查询
     */
    @PostMapping("findAllUser/{limit}/{page}")//page当前页，limit长度
    public Result findAllUser(@PathVariable int limit, @PathVariable int page,@RequestBody UserVo userVo){
        Page<User> pages = new Page<>(page,limit);
        IPage<User> userPage = userService.selectPage(pages, userVo);
        return Result.ok(userPage);
    }

    /**
     * 根据id获取用户详细信息
     */
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable int id){
        User byId = userService.getById(id);
        return Result.ok(byId);
    }

    /**
     * 修改用户信息（不包括密码），不能修改手机号
     * 点击查看用户详情后查找了单个用户信息，在单个用户详细信息中修改信息能获取到用户id
     */
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user){
        userService.updateById(user);
        return Result.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("deleteUser/{id}")
    public Result deleteUser(@PathVariable int id){
        userService.removeById(id);
        return Result.ok();
    }

    /**
     * 根据id集合批量删除用户
     */
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean b = userService.removeByIds(ids);
        if (b == true){
            return Result.ok();
        }
        return Result.fail();
    }
}
