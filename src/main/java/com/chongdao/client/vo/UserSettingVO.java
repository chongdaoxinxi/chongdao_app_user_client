package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSettingVO {
    private String name;//昵称
    private Integer userId;//用户id
    private String phone;//手机
    /** 头像 */
    private String icon;
    private Integer type;
    /** 验证码 */
    private String code;
}
