package com.chongdao.client.entitys;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/** 
 * @Author onlineS
 * @Description 微信支付信息(APP不用此表)
 * @Date 9:39 2019/4/19
 */
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wx_pay_msg")
public class WxPayMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String prepayId;
    private String nonceStr;
    private String openId;
    private String mchId;
    private String outTradeNo;
    private String appId;
    private String resultCode;
    private String returnCode;
    private String orderNo;
    private String shopName;
    private BigDecimal payOffAmount;
    private String goodsName;
    private Integer orderId;

    @Override
    public String toString() {
        return "WxPayMsg{" +
                "prepayId='" + prepayId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", openId='" + openId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", appId='" + appId + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", returnCode='" + returnCode + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", shopName='" + shopName + '\'' +
                ", payOffAmount=" + payOffAmount +
                ", goodsName='" + goodsName + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
