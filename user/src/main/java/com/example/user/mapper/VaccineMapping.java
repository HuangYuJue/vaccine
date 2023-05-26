package com.example.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Vaccine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VaccineMapping extends BaseMapper<Vaccine> {
}
