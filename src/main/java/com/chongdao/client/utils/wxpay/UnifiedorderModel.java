package com.chongdao.client.utils.wxpay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description 统一下单支付实体
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
public class UnifiedorderModel {
    /**
     * 公众号appid
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 随机字符串
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名方式
     */
    private String sign_type;


    /**
     * 商品描述：腾讯充值中心-QQ会员充值
     */
    private String body;
    /**
     * 商品详情
     **/
    private String detail;
    /**
     * 附加数据
     **/
    private String attach;
    /**
     * 支付订单号
     */
    private String out_trade_no;
    /**
     * 总金额(分)
     */
    private Integer total_fee;
    /**
     * 终端IP(8.8.8.8)
     */
    private String spbill_create_ip;
    /**
     * 异步通知地址
     */
    private String notify_url;
    /**
     * 交易类型
     **/
    private String trade_type;


    /**
     * 用户标识
     **/
    private String openid;
    /**
     * 商品id
     **/
    private String product_id;
}
