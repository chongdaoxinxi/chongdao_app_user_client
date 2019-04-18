package com.chongdao.client.common;

import com.chongdao.client.enums.ResultEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;


/**
 * 统一响应结构体
 * @param <T>
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse<T> implements Serializable {

    private int status;

    private String message;

    private T data;

    private ResultResponse(int status){
        this.status = status;
    }

    private ResultResponse(int status,String msg){
        this.status = status;
        this.message = msg;
    }

    private ResultResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ResultResponse(int status,String msg,T data){
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResultEnum.SUCCESS.getCode();
    }

    public static <T> ResultResponse<T> createBySuccess(){
        return new ResultResponse<T>(ResultEnum.SUCCESS.getCode());
    }

    public static <T> ResultResponse<T> createBySuccessMessage(String msg){
        return new ResultResponse<T>(ResultEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ResultResponse<T> createBySuccess(T data){
        return new ResultResponse<T>(ResultEnum.SUCCESS.getCode(),data);
    }

    public static <T> ResultResponse<T> createBySuccess(String msg,T data){
        return new ResultResponse<T>(ResultEnum.SUCCESS.getCode(),msg,data);
    }

    public static <T> ResultResponse<T> createByError(){
        return new ResultResponse<T>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage());
    }

    public static <T> ResultResponse<T> createByErrorMessage(String errorMessage){
        return new ResultResponse<T>(ResultEnum.ERROR.getCode(),errorMessage);
    }

    public static <T> ResultResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ResultResponse<T>(errorCode,errorMessage);
    }


}
