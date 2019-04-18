package com.chongdao.client.exception;

import com.chongdao.client.enums.ResultEnum;

/**
 * 统一异常类
 */
public class PetException extends RuntimeException{

    /** 状态码 */
    private Integer code;

    public PetException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public PetException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
