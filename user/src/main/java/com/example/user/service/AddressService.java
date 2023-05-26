package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.Address;

import java.util.List;

public interface AddressService extends IService<Address> {
    List<Address> findAddress();
}
