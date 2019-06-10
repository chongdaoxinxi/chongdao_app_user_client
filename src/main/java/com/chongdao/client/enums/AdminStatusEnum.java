package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum AdminStatusEnum {
    ADMIN_EXIST_ERROR(410,"用户已存在"),
    ADMIN_CODE_ERROR(411,"验证码错误"),
    ADMIN_NAME_NOT_EMPTY(412,"用户名不能为空"),
    ADMIN_TOKEN_ERROR(413,"Token解析错误"),
    ADMIN_TOKEN_EXPIRED(414,"Token已过期"),
    ADMIN_TOKEN_NOT_EMPTY(415,"Token不能为空"),
    ADMIN_LOGIN_ALREADY(416,"已在别处登录"),
    ADMIN_NAME_OR_PASSWORD_EMPTY(417,"用户名或密码不能为空"),
    ADMIN_NOT_LOGIN_OR_TOKEN_EXPIRED(418,"用户未登录或token已过期"),
    ADMIN_NOT_EXIST_ERROR(419,"用户已存在"),
    ADMIN_ERROR_PASSWORD(420,"用户名或密码不正确"),
    ADMIN_ACCOUNT_FREEZE(421,"帐号已被冻结"),
    ADMIN_ACCOUNT_INFO_ERROR(422, "帐号信息错误"),
    ADMIN_DATA_ERROR(423, "帐号数据错误, 请联系管理员")
    ;

    private int status;
    private String message;

    AdminStatusEnum(int status, String message){
        this.status = status;
        this.message = message;
    }
}
