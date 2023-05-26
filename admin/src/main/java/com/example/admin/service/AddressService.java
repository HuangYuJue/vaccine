package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Address;

import java.util.List;

public interface AddressService extends IService<Address> {
    //添加接种点
    void add(Address address);

    //删除(下线)
    void delete(int id);

    //分页查询
    IPage<Address> selectPage(Page<Address> pages, Address address);

    //修改
    void update(Address address);

    //根据接种点名称获取信息
    List<Integer> getAddress(String address);

    //取消删除(上线)
    void cancel(int id);
}
