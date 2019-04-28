package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginVO {
    private String name;
    private Integer userId;
    private String token;
    private String phone;


    @JsonIgnore
    private String password;

    /** 验证码 */
    private String code;

    private Integer type;
    /** 头像 */
    private String icon;

    /** 用户余额（充值） */
    private BigDecimal money;

    /** 集分 */
    private Integer points = 0;

    private Date lastLoginTime;


}
