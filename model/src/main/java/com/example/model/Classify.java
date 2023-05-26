package com.example.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 疫苗分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classify {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String claname;
    @TableField(value = "is_delete")
    private int deleted;//1:未删除，2:逻辑删除

    @TableField(exist = false)
    private List<Vaccine> vaccineList;//同一种类型的疫苗集合
}
