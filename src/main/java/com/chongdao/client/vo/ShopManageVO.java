package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ShopManageVO {
    private Integer id;
    private String shopName;
    private String phone;
    private Integer areaId;
    private String areaCode;
    private String logo;
    private BigDecimal money;
    private Double grade;//评分
    /**  商家二维码 */
    private String qrCodeUrl;
    private Byte isHot;//是否热门商家
    private Byte isAutoAccept;//是否自动接单
    private Byte isStop;//是否停止营业
    /** -1:停业;0:休息;1:营业中 */
    private Integer status;
}
