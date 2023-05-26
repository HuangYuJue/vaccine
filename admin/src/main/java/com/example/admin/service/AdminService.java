package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Admin;
import com.example.vo.LoginPwVo;

import java.util.Map;

public interface AdminService extends IService<Admin> {
    //登录
    Map<String, Object> login(LoginPwVo loginPwVo);

    //根据account查找用户
    Admin selectByAccount(String account);

}
