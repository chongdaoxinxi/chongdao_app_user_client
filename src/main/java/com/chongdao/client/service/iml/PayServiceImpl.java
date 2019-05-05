package com.chongdao.client.service.iml;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.config.AliPayConfig;
import com.chongdao.client.entitys.OrderDetail;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.PayInfo;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.PayPlatformEnum;
import com.chongdao.client.mapper.OrderDetailMapper;
import com.chongdao.client.mapper.OrderInfoMapper;
import com.chongdao.client.repository.PayInfoRepository;
import com.chongdao.client.service.PayService;
import com.chongdao.client.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private PayInfoRepository payInfoRepository;


    /**
     * 支付宝对接
     * @param orderNo
     * @param userId
     * @return
     */
    @Override
    public ResultResponse aliPay(String orderNo, Integer userId) {
        OrderInfo order = orderInfoMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if (order == null){
            return ResultResponse.createByErrorMessage("用户没有该订单");
        }
        String orderStr = "";
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            //实例化客户端
            /*
         * 开放平台SDK封装了签名实现，只需在创建DefaultAlipayClient对象时，
         * 设置请求网关(gateway)，应用id(app_id)，应用私钥(private_key)，编码格式(charset)，支付宝公钥(alipay_public_key)，签名类型(sign_type)即可，
         * 报文请求时会自动进行签名
         * */
            AlipayClient client = new DefaultAlipayClient(AliPayConfig.GATEWAY, AliPayConfig.ALI_PAY_APPID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT,
                    AliPayConfig.CHARSET, AliPayConfig.ALI_PAY_PUBLIC_KEY,AliPayConfig.SIGN_TYPE);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//                model.setBody(orderMap.get("body"));                      //商品信息
            List<OrderDetail> orderItemList = orderDetailMapper.getByOrderNoUserId();
            model.setSubject(orderItemList.get(0).getName());//商品名称
            model.setOutTradeNo(orderNo);//商户订单号
            model.setTimeoutExpress("120m"); //交易超时时间
            model.setTotalAmount(String.valueOf(order.getPayment())); //支付金额
            model.setProductCode("FAST_INSTANT_TRADE_PAY"); //销售产品码
            //            model.setSellerId(UID);                        //商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AliPayConfig.NOTIFY_URL); //App支付异步回调地址
            ali_request.setReturnUrl(AliPayConfig.RETURN_URL); //付款完成跳转页面
            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            orderStr = response.getBody();
            if(StringUtils.isBlank(orderStr)){
                return ResultResponse.createBySuccessMessage("支付宝预下单失败!!!");
            }
            resultMap.put("orderStr",orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("status","200");
            resultMap.put("message","支付宝预下单成功");
            log.info("支付宝App支付:支付订单创建成功out_trade_no----",order.getOrderNo());
        } catch (Exception e) {
            resultMap.put("status","500");
            resultMap.put("message","支付宝预下单失败!!!");
            log.error("支付宝App支付：支付订单生成失败out_trade_no----",order.getOrderNo());
        }
        return ResultResponse.createBySuccess(resultMap);
    }

    /**
     * 支付宝异步回调
     * @param params
     * @return
     */
    @Override
    public ResultResponse aliCallback(Map<String, String> params) {
        String orderNo = String.valueOf(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        OrderInfo order = orderInfoMapper.selectByOrderNo(orderNo);
        if(order == null){
            log.error("【支付回调】订单不存在:orderNo:{}",orderNo);
            return ResultResponse.createByErrorMessage("非该商铺的订单,回调忽略");
        }
        if(order.getOrderStatus() >= OrderStatusEnum.PAID.getStatus()){
            log.error("【支付回调】支付宝重复调用: orderNo:{}",orderNo);
            return ResultResponse.createBySuccess("支付宝重复调用");
        }
        if(Const.AliPayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setOrderStatus(OrderStatusEnum.PAID.getStatus());
            orderInfoMapper.updateByPrimaryKeySelective(order);
        }

        //生成支付信息
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(PayPlatformEnum.ALI_PAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);

        payInfoRepository.save(payInfo);

        return ResultResponse.createBySuccess();
    }

}
