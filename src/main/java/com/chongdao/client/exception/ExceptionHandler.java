package com.chongdao.client.exception;

import com.chongdao.client.common.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ExceptionHandler{

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResultResponse exceptionHandler(Exception e){
        e.printStackTrace();
        return ResultResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }

}
