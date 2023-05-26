package com.example.exception;

import com.example.model.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }
    @ExceptionHandler(OtherException.class)
    @ResponseBody
    public Result error(OtherException e){
        e.printStackTrace();
        return Result.fail();
    }
}
