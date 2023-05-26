package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.model.Classify;
import com.example.model.Vaccine;
import com.example.user.mapper.VaccineMapping;
import com.example.user.service.ClassifyService;
import com.example.user.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapping, Vaccine> implements VaccineService {

    @Autowired
    private ClassifyService classifyService;

    @Override
    public List<Vaccine> findByName(String vacname) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("vacname", vacname);
        List list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public IPage<Vaccine> findVaccine(Page pages, int classifyId, String vacname) {
        QueryWrapper wrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(vacname)){
            wrapper.like("vacname", vacname);
        }
        wrapper.eq("classify", classifyId);
        wrapper.eq("is_delete", 1);

        IPage<Vaccine> selectPage = baseMapper.selectPage(pages, wrapper);
        //将疫苗类型字段存入
        selectPage.getRecords().stream().forEach(item -> {
            Classify byId = classifyService.getById(classifyId);
            String claname = byId.getClaname();
            item.setClassifyName(claname);
        });
        return selectPage;
    }
}
