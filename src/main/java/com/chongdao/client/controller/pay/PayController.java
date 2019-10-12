package com.chongdao.client.controller.pay;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.config.AliPayConfig;
import com.chongdao.client.service.PayService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付
 */

@RestController
@RequestMapping("/api/pay/")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 支付宝支付
     * @param orderNo
     * @param token
     * @return
     */
    @GetMapping("aliPay")
    public ResultResponse pay(@RequestParam String orderNo, @RequestParam String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return payService.aliPay(orderNo, tokenVo.getUserId());
    }

    /**
     * 支付宝支付(追加)
     * @param reOrderNo
     * @param token
     * @return
     */
    @GetMapping("aliPayRe")
    public ResultResponse aliPayRe(@RequestParam String reOrderNo, @RequestParam String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return payService.aliPayRe(reOrderNo, tokenVo.getUserId());
    }

    /**
     * 支付宝支付(活体)
     * @param htOrderNo
     * @param token
     * @return
     */
    @GetMapping("aliPayHT")
    public ResultResponse payHT(@RequestParam String htOrderNo, @RequestParam String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return payService.payHT(htOrderNo, tokenVo.getUserId());
    }




    /**
     * 回调函数验证参数
     *
     * @param request
     * @return
     */
    @RequestMapping("aliPayCallback")
    public Object aliPayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        //非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

        params.remove("sign_type");
        try {
            boolean aliPayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);

            if (!aliPayRSACheckedV2) {
                log.error("【支付宝回调】非法请求,验证不通过 params = {}",params);
                return ResultResponse.createByErrorMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常", e);
        }

        ResultResponse response = payService.aliCallback(params);
        if (response.isSuccess()) {
            return Const.AliPayCallback.RESPONSE_SUCCESS;
        }
        return Const.AliPayCallback.RESPONSE_FAILED;
    }


    /**
     * 活体支付回调
     * @param request
     * @return
     */
    @RequestMapping("aliPayCallbackHT")
    public Object aliPayCallbackHT(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("活体支付回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        //非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

        params.remove("sign_type");
        try {
            boolean aliPayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);

            if (!aliPayRSACheckedV2) {
                log.error("【活体支付回调】非法请求,验证不通过 params = {}",params);
                return ResultResponse.createByErrorMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            log.error("活体支付回调异常", e);
        }

        ResultResponse response = payService.aliPayCallbackHT(params);
        if (response.isSuccess()) {
            return Const.AliPayCallback.RESPONSE_SUCCESS;
        }
        return Const.AliPayCallback.RESPONSE_FAILED;
    }


    @PostMapping("aliPayInsurance")
    public ResultResponse aliPayInsurance(Integer insuranceFeeRecordId, Integer userId) {
        return payService.aliPayInsurance(insuranceFeeRecordId, userId);
    }

    @PostMapping("aliPayCallbackInsurance")
    public Object aliPayCallbackInsurance(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("保险医疗支付回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        //非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

        params.remove("sign_type");
        try {
            boolean aliPayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);

            if (!aliPayRSACheckedV2) {
                log.error("【保险医疗支付回调】非法请求,验证不通过 params = {}",params);
                return ResultResponse.createByErrorMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            log.error("保险医疗支付回调异常", e);
        }

        ResultResponse response = payService.aliPayCallbackInsurance(params);
        if (response.isSuccess()) {
            return Const.AliPayCallback.RESPONSE_SUCCESS;
        }
        return Const.AliPayCallback.RESPONSE_FAILED;
    }

    /**
     * 微信支付
     *
     * @param req
     * @param orderNo
     * @param totalFee
     * @return
     */
    @PostMapping("/wxPay")
    public ResultResponse wxPay(HttpServletRequest req, String orderNo, Integer totalFee, String goodStr, String openId, Integer payType) {
        return payService.wxPay(req, orderNo, totalFee, goodStr, payType);
    }

    /**
     * 微信支付回调
     *
     * @return
     */
    @RequestMapping("wx_pay_callback")
    public ResultResponse wxPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return payService.wxPayCallback(request, response);
    }
}
