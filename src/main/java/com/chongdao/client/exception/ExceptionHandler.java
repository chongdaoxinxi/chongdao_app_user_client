package com.chongdao.client.exception;

import com.chongdao.client.common.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Method;

/**
 * 统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandler{

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResultResponse exceptionHandler(Exception e){
        log.error(e.getMessage(), Method.class);
        return ResultResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }

}
