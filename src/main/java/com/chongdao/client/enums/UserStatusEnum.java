package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    USER_EXIST_ERROR(410,"用户已存在"),
    USER_CODE_ERROR(411,"验证码错误"),
    USERNAME_NOT_EMPTY(412,"用户名不能为空"),
    TOKEN_ERROR(413,"Token解析错误"),
    TOKEN_EXPIRED(414,"Token已过期"),
    TOKEN_NOT_EMPTY(415,"Token不能为空"),
    USER_LOGIN_ALREADY(416,"已在别处登录"),
    USERNAME_OR_CODE_EMPTY(417,"手机号码或验证码不能为空"),
    USER_NOT_LOGIN_OR_TOKEN_EXPIRED(418,"用户未登录或token已过期"),
    USER_NOT_LOGIN(419,"用户未登录或登录失效,请重新登录"),
    ;

    private int status;

    private String message;

    UserStatusEnum(int status,String message){
        this.status = status;
        this.message = message;
    }
}
