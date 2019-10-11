package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/11
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendInfoVO {
    private String recommendCode;//推广码
    private String recommendUrl;//推广注册链接
    private String qrCodeUrl;//二维码链接
    private String icon;
}
