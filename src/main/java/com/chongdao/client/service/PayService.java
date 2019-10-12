package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface PayService {

    /**
     * 支付宝对接
     * @param orderNo
     * @param userId
     * @return
     */
    ResultResponse aliPay(String orderNo, Integer userId);

    /**
     * 支付宝异步回调
     * @param params
     * @return
     */
    ResultResponse aliCallback(Map<String,String> params);

    /**
     * 微信支付
     * @param req
     * @param orderNo
     * @param totalFee
     * @param goodStr
     * @param payType
     * @return
     */
    ResultResponse wxPay(HttpServletRequest req, String orderNo, Integer totalFee, String goodStr, Integer payType);

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    ResultResponse wxPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException;

    ResultResponse aliPayRe(String reOrderNo, Integer userId);

    ResultResponse payHT(String htOrderNo, Integer userId);

    ResultResponse aliPayCallbackHT(Map<String, String> params);

    /**
     * 保险医疗费用支付(支付宝)
     * @param orderId
     * @param userId
     * @return
     */
    ResultResponse aliPayInsurance(Integer orderId, Integer userId);

    /**
     * 保险医疗费用支付回调(支付宝)
     * @param params
     * @return
     */
    ResultResponse aliPayCallbackInsurance(Map<String, String> params);
}
