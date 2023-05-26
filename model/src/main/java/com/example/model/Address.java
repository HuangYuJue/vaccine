package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 接种点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @TableId(type = IdType.AUTO)
    private int id;
    private String addrname;
    private String opentime;
    @TableField(value = "is_delete")
    private int deleted;//1:未删除，2:逻辑删除

    @TableField(exist = false)
    private List<Worker> workerList;//同一个接种点的工作人员集合
}
