package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/15
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
public class ExpressVO {
    private Integer id;
    private String username;
    private String password;
    private String nativePassword;
    private String name;
    private String phone;
    private Integer type;
    private Integer status;
    private String areaCode;
    private String areaName;
}
