package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {

    private String userName;
    private String vaccineName;
    private String workerName;
    private int vacstatus;//2:未接种，1:已接种
    private int cancel;//2:否，1:是（取消）

    private String addressName;

}
