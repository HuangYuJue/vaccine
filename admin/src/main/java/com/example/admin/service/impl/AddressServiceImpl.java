package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.mapper.AddressMapper;
import com.example.admin.service.AddressService;
import com.example.admin.service.WorkerService;
import com.example.exception.OtherException;
import com.example.model.Address;
import com.example.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private WorkerService workerService;

    @Override
    public void add(Address address) {
        address.setDeleted(1);
        int insert = baseMapper.insert(address);
        if (insert <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public void delete(int id) {
        Address address = baseMapper.selectById(id);
        if (address == null){
            throw new OtherException(ResultEnum.FAIL);
        }
        address.setDeleted(2);
        int i = baseMapper.updateById(address);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public IPage<Address> selectPage(Page<Address> pages, Address address) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        String addrname = address.getAddrname();
        String opentime = address.getOpentime();
        int deleted = address.getDeleted();
        if (!StringUtils.isEmpty(addrname)){
            wrapper.like("addrname", addrname);
        }
        if (!StringUtils.isEmpty(opentime)){
            wrapper.like("opentime", opentime);
        }
        if (deleted != 0){
            wrapper.eq("is_delete", address.getDeleted());
        }
        Page<Address> addressPage = baseMapper.selectPage(pages, wrapper);
        return addressPage;
    }

    @Override
    public void update(Address address) {
        int id = address.getId();
        Address address1 = baseMapper.selectById(id);
        int deleted = address1.getDeleted();
        address.setDeleted(deleted);
        int i = baseMapper.updateById(address);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    //先模糊查询出所有的id，然后根据id查找出address在这些id中的worker的id
    @Override
    public List<Integer> getAddress(String address) {
        QueryWrapper wrapper = new QueryWrapper();
        //模糊查询所有的地点
        wrapper.like("addrname", address);
        List<Address> list1 = baseMapper.selectList(wrapper);
        List<Integer> aids = new ArrayList<>();
        for (Address item1 : list1){
            aids.add(item1.getId());
        }
        List<Integer> wids = workerService.getByIds(aids);
        return wids;
    }

    @Override
    public void cancel(int id) {
        Address address = baseMapper.selectById(id);
        if (address == null){
            throw new OtherException(ResultEnum.FAIL);
        }
        address.setDeleted(1);
        int i = baseMapper.updateById(address);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }
}
