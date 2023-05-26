package com.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 工作人员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
    private int id;
    private String workername;
    @TableField("address")
    private int addressId;
    @TableField(exist = false)
    private String addressName;
    private String worktime;//工作时间

    @TableField(exist = false)
    private List<Order> orderList;//同一个工作人员负责界中的订单集合
}
