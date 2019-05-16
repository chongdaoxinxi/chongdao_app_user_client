package com.chongdao.client.enums;

import lombok.Getter;

/**
 * @Description 商户端枚举类
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@Getter
public enum  ShopManageStatusEnum {
    SHOP_EXIST_ERROR(410,"用户已存在"),
    SHOP_CODE_ERROR(411,"验证码错误"),
    SHOP_NAME_NOT_EMPTY(412,"用户名不能为空"),
    SHOP_TOKEN_ERROR(413,"Token解析错误"),
    SHOP_TOKEN_EXPIRED(414,"Token已过期"),
    SHOP_TOKEN_NOT_EMPTY(415,"Token不能为空"),
    SHOP_LOGIN_ALREADY(416,"已在别处登录"),
    SHOP_NAME_OR_PASSWORD_EMPTY(417,"用户名或密码不能为空"),
    SHOP_NOT_LOGIN_OR_TOKEN_EXPIRED(418,"用户未登录或token已过期"),
    SHOP_NOT_EXIST_ERROR(419,"用户已存在"),
    SHOP_ERROR_PASSWORD(420,"用户名或密码不正确"),
    ;

    private int status;
    private String message;

    ShopManageStatusEnum(int status,String message){
        this.status = status;
        this.message = message;
    }
}
