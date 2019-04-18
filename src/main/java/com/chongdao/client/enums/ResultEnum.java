package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200, "成功"),


    USER_EXIST_ERROR(410,"用户已存在"),
    USER_PWD_ERROR(411,"密码错误"),
    USERNAME_NOT_EMPTY(412,"用户名不能为空"),
    TOKEN_ERROR(413,"Token解析错误"),
    TOKEN_EXPIRED(414,"Token已过期"),
    TOKEN_NOT_EMPTY(415,"Token不能为空"),
    USER_LOGIN_ALREADY(416,"已在别处登录"),

    ERROR(500,"服务器错误"),
    PARAM_ERROR(501, "参数不正确"),

    ;

    /** 错误码 */
    private Integer code;

    /** 错误信息 */
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
