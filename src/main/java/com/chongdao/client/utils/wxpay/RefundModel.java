package com.chongdao.client.utils.wxpay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description 微信退款实体
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
public class RefundModel {
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
     * 支付订单号
     */
    private String out_trade_no;
    /**
     * 退款订单号
     */
    private String out_refund_no;
    /**
     * 总金额
     */
    private Integer total_fee;
    /**
     * 退款金额
     */
    private Integer refund_fee;
    /**
     * 退款原因
     */
    private String refund_desc;
}
