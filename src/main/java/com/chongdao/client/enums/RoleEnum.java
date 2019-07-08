package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    SUPER_ADMIN_PC("SUPER_ADMIN_PC", "超级管理员"),
    ADMIN_PC("ADMIN_PC", "地区管理员"),
    SHOP_PC("SHOP_PC", "商家");

    private String code;
    private String name;

    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
