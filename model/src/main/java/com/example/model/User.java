package com.example.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String phone;
    private String username;
    private String password;
    private int sex;//0:男，1：女
    private int age;

    @TableField(exist = false)
    private List<Order> orderList;//同一个用户的不同疫苗订单的集合

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
