package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.mapper.OrderMapper;
import com.example.admin.service.*;
import com.example.client.AdminClient;
import com.example.exception.OtherException;
import com.example.model.*;
import com.example.utils.ResultEnum;
import com.example.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private VaccineService vaccineService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private AddressService addressService;

    @Autowired
    private AdminClient adminClient;

    //用户名、疫苗名称、工作人员名称、接种点名称、接种状态、支付状态
    @Override
    public IPage<Order> selectPage(Page<Order> pages, OrderVo orderVo) {
        String userName = orderVo.getUserName();
        String vaccineName = orderVo.getVaccineName();
        String workerName = orderVo.getWorkerName();
        String addressName = orderVo.getAddressName();
        int vacstatus = orderVo.getVacstatus();
        int cancel = orderVo.getCancel();
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userName)){
            //根据用户名获取列表
            List uids = adminClient.getUserByName(userName);
            wrapper.in("user_id", uids);
        }
        if (!StringUtils.isEmpty(vaccineName)){
            //根据疫苗名称获取列表
            List vids = adminClient.getVaccineByName(vaccineName);
            wrapper.in("vaccine", vids);
        }
        if (!StringUtils.isEmpty(workerName)){
            //根据工作人员名称获取列表
            List wids = adminClient.getWorker(workerName);
            wrapper.in("worker", wids);
        }
        if (!StringUtils.isEmpty(addressName)){
            //根据接种点获取工作人员列表
            List wids = adminClient.getAddress(addressName);
            wrapper.in("worker", wids);
        }
        if (!StringUtils.isEmpty(vacstatus)){
            if (vacstatus != 0){
                wrapper.eq("vacstatus", vacstatus);
            }
        }
        if (cancel == 1 || cancel == 2){
            wrapper.eq("cancel", cancel);
        }
        Page<Order> orderPage = baseMapper.selectPage(pages, wrapper);
        //将用户名、疫苗名称、工作人员名称、接种点名称、预约时间，接种时间传入
        orderPage.getRecords().stream().forEach(item -> {
            this.packageParam(item);
        });
        return orderPage;
    }

    public Order packageParam(Order order){
        Map<String, Object> param = new HashMap<>();
        //获取用户名、疫苗名称、工作人员名称、接种点名称、预约时间，接种时间
        int userId = order.getUserId();
        String username = userService.getById(userId).getUsername();
        param.put("username", username);
        int vaccineId = order.getVaccineId();
        String vacname = vaccineService.getById(vaccineId).getVacname();
        param.put("vacname", vacname);
        int workerId = order.getWorkerId();
        String workername = workerService.getById(workerId).getWorkername();
        param.put("workername", workername);
        int addressId = workerService.getById(workerId).getAddressId();
        String addrname = addressService.getById(addressId).getAddrname();
        param.put("addrname", addrname);
        Date ordertime = order.getOrdertime();
        String orderStr = this.dateToStr(ordertime);
        param.put("orderStr", orderStr);
        Date vactime = order.getVactime();
        if (vactime != null){
            String vacStr = this.dateToStr(vactime);
            param.put("vacStr", vacStr);
        }
        order.setParam(param);
        return order;
    }

    public String dateToStr(Date time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(time);
        return dateString;
    }

    @Override
    public void removeById(int id) {
        Order order = baseMapper.selectById(id);
        int cancel = order.getCancel();
        int vacstatus = order.getVacstatus();
        //已取消或已接种
        if (cancel == 1 || vacstatus == 1){
            int i = baseMapper.deleteById(id);
            if (i <= 0){
                throw new OtherException(ResultEnum.FAIL);
            }
        } else {
            throw new OtherException(ResultEnum.DELETE_FAIL);
        }
    }

    @Override
    public void vacstatus(int id) {
        Order order = baseMapper.selectById(id);
        if (order.getCancel() == 2){
            order.setVacstatus(1);
            order.setVactime(new Date());
            baseMapper.updateById(order);
        } else {
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public Order getOrder(int id) {
        Order order = baseMapper.selectById(id);
        order = this.packageParam(order);
        return order;
    }
}
