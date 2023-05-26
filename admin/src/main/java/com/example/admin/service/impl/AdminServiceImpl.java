package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.help.JwtHelper;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.service.AdminService;
import com.example.exception.OtherException;
import com.example.model.Admin;
import com.example.utils.ResultEnum;
import com.example.vo.LoginPwVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {


    @Override
    public Map<String, Object> login(LoginPwVo loginPwVo) {
        String account = loginPwVo.getPhone();
        String password = loginPwVo.getPassword();
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("account",account);
        wrapper.eq("password",password);
        Admin admin = baseMapper.selectOne(wrapper);
        Map<String, Object> map = new HashMap<>();
        if (admin != null){
            String phone = admin.getAccount();
            map.put("phone", phone);
            String token = JwtHelper.createToken(admin.getId(), admin.getAccount());
            map.put("token", token);
            return map;
        } else {
            Admin selectOne = selectByAccount(account);
            if (selectOne == null){
                throw new OtherException(ResultEnum.LOGIN_FAIL);
            }
            throw new OtherException(ResultEnum.LOGIN_ERROR);
        }
    }

    @Override
    public Admin selectByAccount(String account) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("account",account);
        Admin admin = baseMapper.selectOne(wrapper);
        return admin;
    }

}
