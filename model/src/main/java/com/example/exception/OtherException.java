package com.example.exception;

import com.example.utils.ResultEnum;
import lombok.Data;

/**
 * 自定义全局异常类
 */
@Data
public class OtherException extends RuntimeException{
    private Integer code;//异常状态码

    public OtherException(String message, Integer code){
        super(message);
        this.code = code;
    }

    public OtherException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
