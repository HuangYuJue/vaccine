package com.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 疫苗详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine implements Serializable {
    private int id;
    private String vacname;
    private BigDecimal price;
    private String vactype;//服用类型
    @TableField("classify")
    private int classifyId;
    @TableField(exist = false)
    private String classifyName;//服用类型名称
    private Integer vaccount;
    private String introduce;
    @TableField(value = "is_delete")
    private int deleted;//1:未删除，2:逻辑删除

    @TableField(exist = false)
    private List<Order> orderList;//同一种疫苗的不同用户订单集合
}
