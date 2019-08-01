package com.chongdao.client.vo;

import com.chongdao.client.entitys.coupon.CouponInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ShopVO {

    private Integer id;
    private String shopName;
    private String phone;
    private Integer areaId;
    private String areaCode;
    private Double lat;
    private Double lng;
    private String logo;
    private BigDecimal money;
    private Integer type;
    private String address;
    private Double grade;//评分
    private String wxNo;
    private String zfbNo;
    private String bankNo;
    /**  商家二维码 */
    private String qrCodeUrl;
    private String des;//店铺描述
    private String showImg;//店铺展示图片
    /** -1:停业;0:休息;1:营业中 */
    private Integer status;
    /** 提现服务费比例 */
    private Integer servicePriceRatio;
    private String stopNote;//停止营业公告

    private Byte isHot;//是否热门商家
    private Byte isAutoAccept;//是否自动接单
    private Byte isStop;//是否停止营业


    /** 参与公益：0，未参与，1参与 */
    private Byte isJoinCommonWeal;

    /** 营业时间 */
    private String  startBusinessHours;

    /** 结束营业时间 */
    private String  endBusinessHours;

    //待删除
    private List<CouponVO> couponVOList;

    private List<CouponInfo> couponInfoList;

    private Double discount;

    private BigDecimal discountPrice;

    private Integer sales;


    //购物车中选中商品或者服务数目
    private Integer checkedCount;

    private Integer userId;

    private String distance;

    //收藏状态  1 收藏 0取消收藏
    private Integer concernStatus;
}
