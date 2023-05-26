package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.model.Address;
import com.example.user.mapper.AddressMapping;
import com.example.user.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapping, Address> implements AddressService {
    @Override
    public List<Address> findAddress() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_delete", 1);
        List list = baseMapper.selectList(wrapper);
        return list;
    }
}
