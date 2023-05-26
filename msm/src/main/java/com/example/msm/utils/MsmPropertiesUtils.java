package com.example.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsmPropertiesUtils implements InitializingBean {

    @Value("${tencent.sms.secretId}")
    private String secretId;
    @Value("${tencent.sms.keysecret}")
    private String keysecret;
    @Value("${tencent.sms.smsSdkAppId}")
    private String smsSdkAppID;
    @Value("${tencent.sms.signName}")
    private String signName;
    @Value("${tencent.sms.templateId}")
    private String templateID;

    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String SMSSDKAPP_ID;
    public static String SIGN_NAME;
    public static String TEMPLATE_ID;
    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = secretId;
        SECRET_KEY = keysecret;
        SMSSDKAPP_ID = smsSdkAppID;
        SIGN_NAME = signName;
        TEMPLATE_ID = templateID;
    }
}
