package com.example.msm.controller;

import com.example.model.Result;
import com.example.msm.service.MsmService;
import com.example.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送手机验证码
     */
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone){
        //获取正确验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            //获取到了直接返回
            return Result.ok();
        }
        code = RandomUtil.getFourCode();
        //获取不到重新生成验证码发送
        boolean isSend = msmService.send(phone, code);
        if (isSend){
            //发送成功并将数据存入redis
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            System.out.println("code:"+redisTemplate.opsForValue().get(phone));
            return Result.ok();
        } else {
            return Result.fail("短信发送失败");
        }
    }
}
