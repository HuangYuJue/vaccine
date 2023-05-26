package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.client.AdminClient;
import com.example.exception.OtherException;
import com.example.model.Order;
import com.example.model.Vaccine;
import com.example.user.mapper.OrderMapping;
import com.example.user.service.OrderService;
import com.example.user.service.UserService;
import com.example.utils.ResultEnum;
import com.example.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapping, Order> implements OrderService {

    @Autowired
    private AdminClient adminClient;

    //添加订单，根据用户id和疫苗id以及工作人员id，所以传入的order中只有这三个参数
    public void add(Order order){
        //预约时间
        Date orderDate = new Date();
        int vaccineId = order.getVaccineId();
        Vaccine vaccine = adminClient.getVaccine(vaccineId);
        //疫苗是否存在
        Integer vaccount = vaccine.getVaccount();
        if (vaccount < 1){
            //直接返回，无任何操作，无响应
            return;
        }
        //疫苗费用
        BigDecimal price = vaccine.getPrice();

        order.setOrdertime(orderDate);
        order.setCost(price);
        order.setVacstatus(2);
        order.setCancel(2);
        //添加订单
        int insert = baseMapper.insert(order);
        if (insert <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
        //添加成功后需将库存-1
        vaccine.setVaccount(vaccount-1);
        adminClient.updateVaccine(vaccine);
    }

    @Override
    public void cancel(int id) {
        Order order = baseMapper.selectById(id);
        //判断是否已经接种
        int vacstatus = order.getVacstatus();
        if (vacstatus == 1){
            return;//如果已经接种，则不执行，无响应
        } else if (vacstatus == 2){
            //取消订单
            order.setCancel(1);
            baseMapper.updateById(order);
            //将疫苗数量恢复
            int vaccineId = order.getVaccineId();
            Vaccine vaccine = adminClient.getVaccine(vaccineId);
            Integer vaccount = vaccine.getVaccount();
            vaccine.setVaccount(vaccount+1);
            adminClient.updateVaccine(vaccine);
        }
    }

    @Override
    public IPage<Order> selectOld(Integer userId, Page<Order> pages, OrderVo orderVo) {
        String vaccineName = orderVo.getVaccineName();
        int vacstatus = orderVo.getVacstatus();
        int cancel = orderVo.getCancel();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        if (!StringUtils.isEmpty(vaccineName)){
            List vids = adminClient.getVaccineByName(vaccineName);
            wrapper.in("vaccine", vids);
        }
        if (vacstatus != 0){
            wrapper.eq("vacstatus", vacstatus);
        }
        if (cancel != 0){
            wrapper.eq("cancel", cancel);
        }
        Page<Order> selectPage = baseMapper.selectPage(pages, wrapper);
        //将疫苗名称、工作人员名称、接种点名称、预约时间，接种时间传入
        selectPage.getRecords().stream().forEach(item -> {
            this.packageParam(item);
        });
        return selectPage;
    }
    public Order packageParam(Order order){
        Map<String, Object> param = new HashMap<>();
        //获取疫苗名称、工作人员名称、接种点名称、预约时间，接种时间
        int vaccineId = order.getVaccineId();
        String vacname = adminClient.getVaccine(vaccineId).getVacname();
        param.put("vacname", vacname);
        int workerId = order.getWorkerId();
        String workername = adminClient.getWorkerById(workerId).getWorkername();
        param.put("workername", workername);
        int addressId = adminClient.getWorkerById(workerId).getAddressId();
        String addrname = adminClient.getAddressById(addressId).getAddrname();
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

}
