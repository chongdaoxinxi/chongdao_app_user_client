package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminInfoVO {
    private Integer userId;
    private String name;
    private String avatar;
    private String[] access;
}
