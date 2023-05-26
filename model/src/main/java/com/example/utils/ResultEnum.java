package com.example.utils;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200,"操作成功"),
    FAIL(201,"操作失败"),
    LOGIN_ERROR(202,"密码错误"),
    REGISTER_ERROR(203,"注册失败"),
    LOGIN_FAIL(204,"手机号或验证码错误"),
    REGISTER_FAIL(205,"已经注册"),
    SEND_FAIL(206,"短信发送失败"),
    CODE_FAIL(207,"验证码错误"),
    DELETE_FAIL(208,"不符合删除规则")
    ;
    private Integer code;
    private String message;
    private ResultEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
