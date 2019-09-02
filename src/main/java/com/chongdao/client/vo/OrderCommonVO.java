package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


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

    //接地址
    private Integer receiveAddressId;

    //送地址
    private Integer deliverAddressId;

    //单程 服务类型:1.家到宠物店，2.宠物店到家
    private Integer singleServiceType = 0;

    private Integer shopId;


    private Integer orderType;

    private Byte follow;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date receiveTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date deliverTime;

}
