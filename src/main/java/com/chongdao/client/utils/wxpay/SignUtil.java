package com.chongdao.client.utils.wxpay;

import com.chongdao.client.dto.WxUnifiedorderModelDTO;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
public class SignUtil {
    /**
     * 微信统一签名
     *
     * @param params
     * @param mchKey 商户密钥
     * @return
     */
    public static String sign(Map<String, String> params, String mchKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);
        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (value != null && !"".equals(value) && !"sign".equals(key)
                    && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        toSign.append("key=").append(mchKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 创建统一下单签名map
     *
     * @param request
     * @return
     */
    public static Map<String, String> createUnifiedSign(WxUnifiedorderModelDTO request) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", request.getAppid());
        map.put("mch_id", request.getMch_id());
        map.put("nonce_str", request.getNonce_str());
        map.put("sign", request.getSign());
        map.put("sign_type", request.getSign_type());
        map.put("attach", request.getAttach());
        map.put("body", request.getBody());
        map.put("detail", request.getDetail());
        map.put("notify_url", request.getNotify_url());
        map.put("openid", request.getOpenid());
        map.put("out_trade_no", request.getOut_trade_no());
        map.put("spbill_create_ip", request.getSpbill_create_ip());
        map.put("total_fee", String.valueOf(request.getTotal_fee()));
        map.put("trade_type", request.getTrade_type());
        return map;
    }
}
