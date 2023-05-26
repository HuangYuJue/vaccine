package com.example.msm.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 生成验证码工具类
 */
public class RandomUtil {
    private static final Random random = new Random();
    private static final DecimalFormat fourCode = new DecimalFormat("0000");
    public static String getFourCode(){
        return fourCode.format(random.nextInt(10000));
    }
}
