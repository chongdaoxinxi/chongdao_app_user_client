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

    ResultResponse wxPay(HttpServletRequest req, String orderNo, Integer totalFee, String goodStr);

    ResultResponse wxPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
