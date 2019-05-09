package com.chongdao.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description 微信下单预付单返回信息
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
public class WxUnifiedorderResponseDTO {
    /**
     * 预付单信息
     **/
    private String prepay_id;
    /**
     * 公众号appid
     **/
    private String appid;
    /**
     * 商户号id
     **/
    private String mch_id;
    /**
     * 随机字符串
     **/
    private String nonce_str;
    /**
     * 签名
     **/
    private String sign;
    /**
     * 交易类型/请求方式-APP
     **/
    private String trade_type;
    /**
     * 结果码
     **/
    private String result_code;
    /**
     * 返回码
     **/
    private String return_code;
    /**
     * 返回提示信息
     **/
    private String return_msg;
    /**
     * 时间戳
     **/
    private String timestamp;

    @Override
    public String toString() {
        return "WxUnifiedorderResponseDTO{" +
                "prepay_id='" + prepay_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", result_code='" + result_code + '\'' +
                ", return_code='" + return_code + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
