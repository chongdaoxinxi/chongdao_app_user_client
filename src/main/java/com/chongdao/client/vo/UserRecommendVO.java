package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/9
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRecommendVO {
    private String name;
    private Integer isLoginApp;//是否已经登录过APP 1: 是 -1: 否
}
