package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.mapper.VaccineMapper;
import com.example.admin.service.ClassifyService;
import com.example.admin.service.VaccineService;
import com.example.exception.OtherException;
import com.example.model.Classify;
import com.example.model.Vaccine;
import com.example.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService {
    @Autowired
    private ClassifyService classifyService;
    @Override
    public IPage<Vaccine> selectPage(Page<Vaccine> pages, Vaccine vaccine) {
        QueryWrapper wrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(vaccine.getVacname())){
            wrapper.like("vacname",vaccine.getVacname());
        }
        if (!StringUtils.isEmpty(vaccine.getVactype())){
            wrapper.like("vactype",vaccine.getVactype());
        }
        if (vaccine.getClassifyId() != 0){
            wrapper.eq("classify", vaccine.getClassifyId());
        }
        if (vaccine.getVaccount() != null){
            wrapper.eq("vaccount",vaccine.getVaccount());
        }
        if (vaccine.getDeleted() == 1 || vaccine.getDeleted() == 2){
            wrapper.eq("is_delete",vaccine.getDeleted());
        }
        IPage<Vaccine> selectPage = baseMapper.selectPage(pages, wrapper);
        //将疫苗类型字段存入
        selectPage.getRecords().stream().forEach(item -> {
            int classifyId = item.getClassifyId();
            Classify byId = classifyService.getById(classifyId);
            String claname = byId.getClaname();
            item.setClassifyName(claname);
        });
        return selectPage;
    }

    @Override
    public void insert(Vaccine vaccine) {
        vaccine.setDeleted(1);
        int insert = baseMapper.insert(vaccine);
        if (insert <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public boolean update(Vaccine vaccine) {
        Vaccine vaccine1 = baseMapper.selectById(vaccine.getId());
        int deleted = vaccine1.getDeleted();
        vaccine.setDeleted(deleted);
        int i = baseMapper.updateById(vaccine);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
        return true;
    }

    @Override
    public void remove(int id) {
        Vaccine vaccine = baseMapper.selectById(id);
        if (vaccine == null){
            throw new OtherException(ResultEnum.FAIL);
        }
        vaccine.setDeleted(2);
        int i = baseMapper.updateById(vaccine);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public List<Integer> getVaccineByName(String vacname) {
        QueryWrapper<Vaccine> wrapper = new QueryWrapper<>();
        wrapper.like("vacname", vacname);
        List<Vaccine> list = baseMapper.selectList(wrapper);
        //定义一个list集合存储符合条件的id
        List<Integer> ids = new ArrayList<>();
        for (Vaccine item : list){
            ids.add(item.getId());
        }
        return ids;
    }

    @Override
    public void cancel(int id) {
        Vaccine vaccine = baseMapper.selectById(id);
        if (vaccine == null){
            throw new OtherException(ResultEnum.FAIL);
        }
        vaccine.setDeleted(1);
        int i = baseMapper.updateById(vaccine);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public Vaccine getById(int id) {
        Vaccine vaccine = baseMapper.selectById(id);
        int classifyId = vaccine.getClassifyId();
        //获取接种类型名称
        String classify = classifyService.getById(classifyId).getClaname();
        vaccine.setClassifyName(classify);
        return vaccine;
    }
}
