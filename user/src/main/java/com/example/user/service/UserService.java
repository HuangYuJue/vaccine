package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.User;
import com.example.vo.LoginCodeVo;
import com.example.vo.LoginPwVo;
import com.example.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService extends IService<User> {
    //用户登录/注册，验证码
    Map<String, Object> loginCode(LoginCodeVo loginCodeVo);
    //密码登录
    Map<String, Object> loginPw(LoginPwVo loginPwVo);

    //查询电话号码
    User byPhone(String phone);

    //修改其他资料
    void update(Integer id,UserVo userVo);

    //判断用户密码是否设置过
    String writePw(HttpServletRequest request);

    //设置密码/修改密码
    void setPw(HttpServletRequest request, String pwd);
}
