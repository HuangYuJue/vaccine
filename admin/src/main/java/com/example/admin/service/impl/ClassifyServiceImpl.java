package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.mapper.ClassifyMapper;
import com.example.admin.service.ClassifyService;
import com.example.exception.OtherException;
import com.example.model.Classify;
import com.example.utils.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify> implements ClassifyService {

    @Override
    public IPage<Classify> selectPage(Page<Classify> pages, Classify classify) {
        QueryWrapper<Classify> wrapper = new QueryWrapper();
        int deleted = classify.getDeleted();
        if (deleted != 0){
            wrapper.eq("is_delete", deleted);
        }
        if (!StringUtils.isEmpty(classify.getClaname())){
            wrapper.like("claname",classify.getClaname());
        }
        IPage<Classify> selectPage = baseMapper.selectPage(pages, wrapper);
        selectPage.getRecords().stream().forEach(item -> {
            System.out.println(item);
        });
        return selectPage;
    }

    @Override
    public void insert(Classify classify) {
        classify.setDeleted(1);
        int insert = baseMapper.insert(classify);
        if (insert <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public void delete(int id) {
        Classify classify = baseMapper.selectById(id);
        classify.setDeleted(2);
        int delete = baseMapper.updateById(classify);
        if (delete <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public void update(Classify classify) {
        Classify classify1 = baseMapper.selectById(classify.getId());
        if (classify1 == null){
            throw new OtherException(ResultEnum.FAIL);
        }
        String claname = classify.getClaname();
        if (!StringUtils.isEmpty(claname)){
            classify1.setClaname(claname);
        }
        int i = baseMapper.updateById(classify1);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public void cancel(int id) {
        Classify classify = baseMapper.selectById(id);
        classify.setDeleted(1);
        int i = baseMapper.updateById(classify);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

}
