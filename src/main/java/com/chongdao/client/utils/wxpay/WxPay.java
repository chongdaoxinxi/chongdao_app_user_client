package com.chongdao.client.utils.wxpay;



import com.chongdao.client.dto.WxUnifiedorderModelDTO;
import com.chongdao.client.dto.WxUnifiedorderResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;


/**
 * 微信统一支付服务
 */
public class WxPay {

    /**
     * APP微信支付
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    public static WxUnifiedorderResponseDTO payApp(HttpServletRequest req, WxUnifiedorderModelDTO model) throws Exception {

        model.setNonce_str(PayUtil.getRandomStr());
        model.setSign(SignUtil.sign(SignUtil.createUnifiedSign(model), BasicInfo.APP_MchKey));
        try {
            XMLUtil xmlUtil = new XMLUtil();
            xmlUtil.xstream().alias("xml", model.getClass());

            String xml = xmlUtil.xstream().toXML(model);
            String response = PayUtil.ssl(BasicInfo.unifiedordersurl, xml, req, BasicInfo.APP_MchId);

            WxUnifiedorderResponseDTO ret = (WxUnifiedorderResponseDTO) XMLUtil.fromXML(response, WxUnifiedorderResponseDTO.class);

            System.out.println("-------------------");
            System.out.println(ret.toString());

            if ("SUCCESS".equals(ret.getResult_code())) {
                //再次签名
                Map<String, String> finalpackage = new TreeMap<String, String>();
                String timestamp = (System.currentTimeMillis() / 1000) + "";
                String noncestr = PayUtil.getRandomStr();

                finalpackage.put("appid", BasicInfo.APP_AppID);
                finalpackage.put("timestamp", timestamp);
                finalpackage.put("noncestr", noncestr);
                finalpackage.put("prepayid", ret.getPrepay_id());
                finalpackage.put("package", "Sign=WXPay");
                finalpackage.put("partnerid", BasicInfo.APP_MchId);

                String sign = SignUtil.sign(finalpackage, BasicInfo.APP_MchKey);

                ret.setSign(sign);
                ret.setNonce_str(noncestr);
                ret.setTimestamp(timestamp);

                return ret;
            } else {
                return ret;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
