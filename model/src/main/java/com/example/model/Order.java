package com.example.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_id")
    private int userId;
    @TableField(value = "vaccine")
    private int vaccineId;
    private Date ordertime;//预约时间
    @TableField(value = "worker")
    private int workerId;
    private Date vactime;//接种时间，由工作人员点击已接种改变
    private BigDecimal cost;//费用
    private int vacstatus;//2:未接种，1:已接种
    private int cancel;//是否取消订单，是：1，否：2
    @TableLogic
    @TableField(value = "is_delete")
    private int deleted;//逻辑删除，0：未删除、1：已删除

    @TableField(exist = false)
    private Map<String, Object> param;
    //用于存储用户名，疫苗名称，工作人员名称，接种点名称，预约时间，接种时间
}
