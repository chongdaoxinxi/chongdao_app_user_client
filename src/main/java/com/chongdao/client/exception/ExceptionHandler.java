package com.chongdao.client.exception;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

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
        e.printStackTrace();
       if (e instanceof NoHandlerFoundException){
            return ResultResponse.createByErrorCodeMessage(HttpStatus.SC_NOT_FOUND,ResultEnum.NOT_FOUND.getMessage());
        }else if (e instanceof MethodArgumentTypeMismatchException){
            return ResultResponse.createByErrorCodeMessage(HttpStatus.SC_BAD_REQUEST,
                    "参数类型不匹配,参数" + ((MethodArgumentTypeMismatchException) e).getName()+ "类型应该为" + ((MethodArgumentTypeMismatchException) e).getRequiredType());
        }else if (e instanceof MissingServletRequestParameterException){
            return ResultResponse.createByErrorCodeMessage(HttpStatus.SC_BAD_REQUEST,
                    "缺少必要参数,参数名称为" + ((MissingServletRequestParameterException) e).getParameterName());
        }else if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResultResponse.createByErrorCodeMessage(HttpStatus.SC_METHOD_NOT_ALLOWED,
                    "不支持" + ((HttpRequestMethodNotSupportedException) e).getMethod() + "方法");
        }else    if (e instanceof ServletRequestBindingException){
           return ResultResponse.createByErrorCodeMessage(HttpStatus.SC_NOT_FOUND,ResultEnum.UNKNOWN_ERROR.getMessage());
        }else{
            return ResultResponse.createByErrorCodeMessage(HttpStatus.SC_METHOD_NOT_ALLOWED, ResultEnum.ERROR.getMessage() );
        }
    }

}
