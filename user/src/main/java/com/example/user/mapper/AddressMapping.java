package com.example.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapping extends BaseMapper<Address> {
}
