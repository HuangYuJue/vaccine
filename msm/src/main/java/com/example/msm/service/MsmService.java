package com.example.msm.service;

public interface MsmService {
    //腾讯云发送短信
    boolean send(String phone, String code);
}
