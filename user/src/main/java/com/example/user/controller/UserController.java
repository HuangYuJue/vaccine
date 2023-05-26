package com.example.user.controller;

import com.example.model.Result;
import com.example.model.User;
import com.example.user.help.JwtHelper;
import com.example.user.service.UserService;
import com.example.vo.LoginCodeVo;
import com.example.vo.LoginPwVo;
import com.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/users/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录/注册，验证码
     */
    @PostMapping("loginCode")
    public Result loginCode(@RequestBody LoginCodeVo loginCodeVo){
        Map<String, Object> map = userService.loginCode(loginCodeVo);
        return Result.ok(map);
    }

    /**
     * 密码登录
     */
    @PostMapping("loginPw")
    public Result loginPw(@RequestBody LoginPwVo loginPwVo){
        Map<String, Object> map = userService.loginPw(loginPwVo);
        return Result.ok(map);
    }

    /**
     * 判断用户是否保存过密码
     */
    @GetMapping("get")
    public Result get(HttpServletRequest request){
        String pw = userService.writePw(request);
        return Result.ok(pw);
    }

    /**
     * 设置密码/更新密码
     */
    @PostMapping("setPw/{Pwd}")
    public Result setPwd(HttpServletRequest request,@PathVariable String Pwd){
        userService.setPw(request, Pwd);
        return Result.ok();
    }

    /**
     * 更改基本信息
     */
    @PostMapping("update")
    public Result update(@RequestBody UserVo userVo,HttpServletRequest request){
        String token = request.getHeader("token");
        Integer userId = JwtHelper.getUserId(token);
        System.out.println("userId:"+userId);
        userService.update(userId, userVo);
        return Result.ok();
    }

    @GetMapping("userDetail")
    public Result userDetail(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println("个人信息token："+token);
        Integer userId = JwtHelper.getUserId(token);
        System.out.println("个人信息token的id："+userId);
        User user = userService.getById(userId);
        return Result.ok(user);
    }
}
