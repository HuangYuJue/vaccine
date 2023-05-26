package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.User;
import com.example.vo.UserVo;

import java.util.List;

public interface UserService extends IService<User> {
    //分页查询所有用户数据
    IPage<User> selectPage(Page<User> pages, UserVo userVo);

    //根据用户名获取用户id
    List<Integer> getUserByName(String username);
}
