package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {


    SUCCESS(200, "成功"),

    ERROR(500,"操作失败"),
    PARAM_ERROR(501, "参数不正确"),
    UNKNOWN_ERROR(502,"url绑定路由问题"),
    NOT_FOUND(503,"没有找到对应的访问路径"),

    USER_TYPE_APP(1,"app用户"),
    USER_TYPE_WX(1,"微信用户"),

    ;

    /** 错误码 */
    private Integer status;

    /** 错误信息 */
    private String message;

    ResultEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
