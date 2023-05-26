package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.mapper.WorkerMapper;
import com.example.admin.service.AddressService;
import com.example.admin.service.WorkerService;
import com.example.exception.OtherException;
import com.example.model.Worker;
import com.example.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapper, Worker> implements WorkerService {
    @Autowired
    private AddressService addressService;
    @Override
    public void insert(Worker worker) {
        int insert = baseMapper.insert(worker);
        if (insert <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public void update(Worker worker) {
        Worker select = baseMapper.selectById(worker.getId());
        String worktime = worker.getWorktime();
        String workername = worker.getWorkername();
        int address = worker.getAddressId();
        if (!StringUtils.isEmpty(workername)){
            select.setWorkername(workername);
        }
        if (!StringUtils.isEmpty(worktime)){
            select.setWorktime(worktime);
        }
        if (address > 0){
            select.setAddressId(address);
        }
        int i = baseMapper.updateById(select);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public void delete(int id) {
        int i = baseMapper.deleteById(id);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public List<Worker> getWorker(String workerName) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("workername", workerName);
        List list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public IPage<Worker> select(Page<Worker> pages, Worker worker) {
        QueryWrapper wrapper = new QueryWrapper();
        int addressId = worker.getAddressId();
        String workername = worker.getWorkername();
        if (addressId != 0){
            wrapper.eq("address", addressId);
        }
        if (!StringUtils.isEmpty(workername)){
            wrapper.like("workername", workername);
        }
        IPage<Worker> selectPage = baseMapper.selectPage(pages, wrapper);
        selectPage.getRecords().stream().forEach(item -> {
            int addr = item.getAddressId();
            System.out.println("worker中addressId："+addr);
            String address = addressService.getById(addr).getAddrname();
            item.setAddressName(address);
        });
        return selectPage;
    }

    @Override
    public List<Integer> getByIds(List<Integer> aids) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in("address", aids);
        List<Worker> list = baseMapper.selectList(wrapper);
        List<Integer> wids = new ArrayList<>();
        for (Worker item : list){
            wids.add(item.getId());
        }
        return wids;
    }

    @Override
    public Worker getById(int id) {
        Worker worker = baseMapper.selectById(id);
        int addressId = worker.getAddressId();
        String address = addressService.getById(addressId).getAddrname();
        worker.setAddressName(address);
        return worker;
    }
}
