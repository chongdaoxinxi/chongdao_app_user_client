package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopLoginVO {
    private Integer shopId;
    private String accountName;
    private String token;

    @JsonIgnore
    private String password;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;
}
