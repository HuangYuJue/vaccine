package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.mapper.UserMapper;
import com.example.admin.service.UserService;
import com.example.model.User;
import com.example.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public IPage<User> selectPage(Page<User> pages, UserVo userVo) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(userVo.getPhone())){
            wrapper.like("phone",userVo.getPhone());
        }
        if (!StringUtils.isEmpty(userVo.getUsername())){
            wrapper.like("username",userVo.getUsername());
        }
        IPage<User> iPage = baseMapper.selectPage(pages, wrapper);
        return iPage;
    }

    @Override
    public List<Integer> getUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        List<User> list = baseMapper.selectList(wrapper);
        List<Integer> ids = new ArrayList<>();
        for (User item : list){
            ids.add(item.getId());
        }
        return ids;
    }

}
