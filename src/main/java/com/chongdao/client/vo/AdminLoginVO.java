package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminLoginVO {
    private Integer managementId;
    private String username;
    private String token;
    @JsonIgnore
    private String password;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;
}
