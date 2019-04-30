package com.chongdao.client.vo;

import lombok.Data;


@Data
public class OrderCommonVO {

    //0.商品 1.服务
    private Integer isService;

    // 1.单程,2.双程
    private Integer serviceType;

    private Integer cardId;

    private Integer couponId;

    //1.支付宝 2.微信
    private Integer payType;

}
