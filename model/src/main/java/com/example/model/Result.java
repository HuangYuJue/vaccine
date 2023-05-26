package com.example.model;

import lombok.Data;

/**
 * 全局统一返回结果集
 */
@Data
public class Result<T> {
    //返回码
    private Integer code;
    public Integer getCode() {
        return code;
    }

    //返回消息
    private String message;
    public String getMessage() {
        return message;
    }

    //返回数据
    private T data;
    public T getData() {
        return data;
    }

    private Result(Integer code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    private Result(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    private Result(String message){
        this.message = message;
    }

    //创建一个带有状态、消息和数据的结果对象
    public static <T> Result<T> buildResult(Status status,String message,T data){
        return new Result<T>(status.getCode(),message,data);
    }

    //创建一个带有状态和数据的结果对象
    public static <T> Result<T> buildResult(Status status,T data){
        return new Result<T>(status.getCode(),status.getMessage(),data);
    }

    //创建带有状态的结果对象
    public static <T> Result<T> buildResult(Status status){
        return new Result<T>(status.getCode(),status.getMessage());
    }

    //操作成功
    public static <T> Result<T> ok(T data){
        return buildResult(Status.SUCCESS,data);
    }
    public static <T> Result<T> ok(){
        return Result.ok(null);
    }

    //操作失败
    public static <T> Result<T> fail(T data){
        return buildResult(Status.FAIL,data);
    }
    public static <T> Result<T> fail(){
        return Result.fail(null);
    }

    public enum Status {

        SUCCESS(200,"成功"),
        FAIL(201, "失败"),
        PARAM_ERROR( 202, "参数不正确"),
        SERVICE_ERROR(203, "服务异常"),
        DATA_ERROR(204, "数据异常"),
        DATA_UPDATE_ERROR(205, "数据版本异常"),
        LOGIN_AUTH(208, "未登陆"),
        PERMISSION(209, "没有权限"),
        CODE_ERROR(210, "验证码错误"),
        LOGIN_ERROR(211,"登陆失败");

        private Integer code;
        private String message;

        Status(Integer code,String message){
            this.code = code;
            this.message = message;
        }
        public Integer getCode(){
            return code;
        }
        public String getMessage(){
            return message;
        }
    }
}
