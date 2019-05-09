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
     * 签名
     **/
    private String nonce_str;
    /**
     * 公众号appid
     **/
    private String appid;
    /**
     * 签名
     **/
    private String sign;
    /**
     * 请求方式
     **/
    private String trade_type;
    /**
     * 商户号id
     **/
    private String mch_id;
    /**
     * 返回提示信息
     **/
    private String return_msg;
    /**
     * 结果码
     **/
    private String result_code;
    /**
     * 返回码
     **/
    private String return_code;
    /**
     * 时间戳
     **/
    private String timestamp;
}
