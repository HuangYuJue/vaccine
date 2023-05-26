package com.example.msm.service.impl;

import com.example.msm.service.MsmService;
import com.example.msm.utils.MsmPropertiesUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        if (StringUtils.isEmpty(phone)){
            return false;
        }
        Credential credential = new Credential(MsmPropertiesUtils.SECRET_ID,MsmPropertiesUtils.SECRET_KEY);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        SmsClient smsClient = new SmsClient(credential, "ap-nanjing", clientProfile);
        SendSmsRequest request = new SendSmsRequest();

        String[] phoneNumberSet1 = {"+86"+phone};
        request.setPhoneNumberSet(phoneNumberSet1);

        request.setSmsSdkAppid(MsmPropertiesUtils.SMSSDKAPP_ID);
        request.setSign(MsmPropertiesUtils.SIGN_NAME);
        request.setTemplateID(MsmPropertiesUtils.TEMPLATE_ID);
        String[] templateParamSet1 = {code};
        request.setTemplateParamSet(templateParamSet1);
        try {
            //发送短信
            SendSmsResponse response = smsClient.SendSms(request);
            System.out.println(SendSmsResponse.toJsonString(response));
            return true;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return false;
        }
    }
}
