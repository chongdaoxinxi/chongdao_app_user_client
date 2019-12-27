package com.chongdao.client.service.iml;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.Const;
import com.chongdao.client.common.CouponCommon;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.config.AliPayConfig;
import com.chongdao.client.dto.OrderLogDTO;
import com.chongdao.client.dto.WxUnifiedorderModelDTO;
import com.chongdao.client.dto.WxUnifiedorderResponseDTO;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.PayPlatformEnum;
import com.chongdao.client.enums.PaymentTypeEnum;
import com.chongdao.client.repository.HtOrderInfoRepository;
import com.chongdao.client.repository.InsuranceFeeRecordRepository;
import com.chongdao.client.repository.PayInfoRepository;
import com.chongdao.client.service.*;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.utils.wxpay.BasicInfo;
import com.chongdao.client.utils.wxpay.PayUtil;
import com.chongdao.client.utils.wxpay.SignUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class PayServiceImpl extends CommonRepository implements PayService {


    @Autowired
    private PayInfoRepository payInfoRepository;
    @Autowired
    private InsuranceFeeRecordRepository insuranceFeeRecordRepository;
    @Autowired
    private HtOrderInfoRepository htOrderInfoRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CashAccountService cashAccountService;

    @Autowired
    private CouponCommon couponCommon;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private OrderOperateLogService orderOperateLogService;
    @Autowired
    private InsuranceFeeRecordService insuranceFeeRecordService;

    /**
     * 支付宝对接
     *
     * @param orderNo
     * @param userId
     * @return
     */
    @Override
    public ResultResponse aliPay(String orderNo, Integer userId) {
        OrderInfo order = orderInfoMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ResultResponse.createByErrorMessage("用户没有该订单");
        }
        if (order.getOrderStatus() > -1) {
            return ResultResponse.createByErrorMessage("该订单已支付，请勿重复支付");
        }
        String orderStr = "";
        Map<String, String> resultMap = new HashMap<>();
        try {
            //实例化客户端
            /*
         * 开放平台SDK封装了签名实现，只需在创建DefaultAlipayClient对象时，
         * 设置请求网关(gateway)，应用id(app_id)，应用私钥(private_key)，编码格式(charset)，支付宝公钥(alipay_public_key)，签名类型(sign_type)即可，
         * 报文请求时会自动进行签名
         * */
            AlipayClient client = new DefaultAlipayClient(AliPayConfig.GATEWAY, AliPayConfig.ALI_PAY_APPID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT,
                    AliPayConfig.CHARSET, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.SIGN_TYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//                model.setBody(orderMap.get("body"));                      //商品信息

            model.setSubject("养宠有道订单-" +orderNo);//商品名称
            model.setOutTradeNo(orderNo);//商户订单号
            model.setTimeoutExpress("30m"); //交易超时时间
            System.out.println("支付宝支付金额" + String.valueOf(order.getPayment()));
            model.setTotalAmount(String.valueOf(order.getPayment())); //支付金额
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AliPayConfig.NOTIFY_URL); //App支付异步回调地址
            //ali_request.setReturnUrl(AliPayConfig.RETURN_URL); //付款完成跳转页面
            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            orderStr = response.getBody();
            if (StringUtils.isBlank(orderStr)) {
                OrderLog orderLog = OrderLogDTO.addOrderLog(order);
                orderLog.setNote("支付宝预下单失败");
                logRepository.save(orderLog);
                return ResultResponse.createBySuccessMessage("支付宝预下单失败!!!");
            }
            String orderStrResult = orderStr;
            resultMap.put("orderStr", orderStrResult);//就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("status", "200");
            resultMap.put("message", "支付宝预下单成功");
            OrderLog orderLog = OrderLogDTO.addOrderLog(order);
            orderLog.setNote("支付宝预下单成功");
            orderLog.setOrderStatus(OrderStatusEnum.ORDER_PRE.getStatus());
            logRepository.save(orderLog);
            log.info("支付宝App支付:支付订单创建成功out_trade_no---- {}", order.getOrderNo());
        } catch (Exception e) {
            resultMap.put("status", "500");
            resultMap.put("message", "支付宝预下单失败!!!");
            log.error("支付宝App支付：支付订单生成失败out_trade_no---- {}", order.getOrderNo());
        }
        //从购物车中获取数据
//        List<Carts> cartList = cartsMapper.selectCheckedCartByUserId(userId, order.getShopId(), null);
//        this.cleanCart(cartList);
        return ResultResponse.createBySuccess(resultMap);
    }


    /**
     * 追加订单支付
     *
     * @param reOrderNo
     * @param userId
     * @return
     */
    @Override
    public ResultResponse aliPayRe(String reOrderNo, Integer userId) {
        OrderInfoRe order = orderInfoReRepository.findByReOrderNoAndStatusAndUserId(reOrderNo, -1, userId);
        if (order == null) {
            return ResultResponse.createByErrorMessage("用户没有该订单");
        }
        if (order.getStatus() == 0) {
            return ResultResponse.createByErrorMessage("该订单已支付，请勿重复支付");
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
                    AliPayConfig.CHARSET, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.SIGN_TYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//                model.setBody(orderMap.get("body"));                      //商品信息
            model.setSubject("养宠有道追加订单");//商品名称
            model.setOutTradeNo(reOrderNo);//商户订单号
            model.setTimeoutExpress("120m"); //交易超时时间
            model.setTotalAmount(String.valueOf(order.getPayment())); //支付金额
            model.setProductCode("FAST_INSTANT_TRADE_PAY"); //销售产品码
            //            model.setSellerId(UID);                        //商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AliPayConfig.NOTIFY_URL); //App支付异步回调地址
            ali_request.setReturnUrl(AliPayConfig.RETURN_URL); //付款完成跳转页面
            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            orderStr = response.getBody();
            if (StringUtils.isBlank(orderStr)) {
                OrderLog orderLog = OrderLogDTO.addOrderLogRe(order);
                orderLog.setNote("支付宝预下单失败(追加)");
                logRepository.save(orderLog);
                return ResultResponse.createBySuccessMessage("支付宝预下单失败(追加)!!!");
            }
            String orderStrResult = orderStr;
            resultMap.put("orderStr", orderStrResult);//就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("status", "200");
            resultMap.put("message", "支付宝预下单成功");
            OrderLog orderLog = OrderLogDTO.addOrderLogRe(order);
            orderLog.setNote("支付宝预下单成功(追加)");
            orderLog.setOrderStatus(OrderStatusEnum.ORDER_PRE.getStatus());
            logRepository.save(orderLog);
            log.info("支付宝App支付(追加):支付订单创建成功out_trade_no---- {}", order.getOrderNo());
        } catch (Exception e) {
            resultMap.put("status", "500");
            resultMap.put("message", "支付宝预下单失败(追加)!!!");
            log.error("支付宝App支付(追加)：支付订单生成失败out_trade_no---- {}", order.getOrderNo());
        }
        //从购物车中获取数据
//        List<Carts> cartList = cartsMapper.selectCheckedCartByUserId(userId, order.getShopId(), null);
//        this.cleanCart(cartList);
        return ResultResponse.createBySuccess(resultMap);
    }

    /**
     * 活体支付
     * @param htOrderNo
     * @param userId
     * @return
     */
    @Override
    public ResultResponse payHT(String htOrderNo, Integer userId) {
        HtOrderInfo order = htOrderInfoRepository.findByHtOrderNoAndOrderStatusAndBuyerUserId(htOrderNo, -1, userId);
        if (order == null) {
            return ResultResponse.createByErrorMessage("用户没有该订单");
        }
        if (order.getOrderStatus() == 0) {
            return ResultResponse.createByErrorMessage("该订单已支付，请勿重复支付");
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
                    AliPayConfig.CHARSET, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.SIGN_TYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//                model.setBody(orderMap.get("body"));                      //商品信息
            model.setSubject("养宠有道活体商品");//商品名称
            model.setOutTradeNo(htOrderNo);//商户订单号
            model.setTimeoutExpress("120m"); //交易超时时间
            model.setTotalAmount(String.valueOf(order.getPayment())); //支付金额
            model.setProductCode("FAST_INSTANT_TRADE_PAY"); //销售产品码
            //            model.setSellerId(UID);                        //商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AliPayConfig.NOTIFY_URL_HT); //App支付异步回调地址
            ali_request.setReturnUrl(AliPayConfig.RETURN_URL); //付款完成跳转页面
            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            orderStr = response.getBody();
            if (StringUtils.isBlank(orderStr)) {
                OrderLog orderLog = OrderLogDTO.addOrderLogHT(order);
                orderLog.setNote("支付宝预下单失败(活体)");
                logRepository.save(orderLog);
                return ResultResponse.createBySuccessMessage("支付宝预下单失败(追加)!!!");
            }
            String orderStrResult = orderStr;
            resultMap.put("orderStr", orderStrResult);//就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("status", "200");
            resultMap.put("message", "支付宝预下单成功");
            OrderLog orderLog = OrderLogDTO.addOrderLogHT(order);
            orderLog.setNote("支付宝预下单成功(活体)");
            orderLog.setOrderStatus(OrderStatusEnum.ORDER_PRE.getStatus());
            logRepository.save(orderLog);
            log.info("支付宝App支付(活体):支付订单创建成功out_trade_no---- {}", order.getHtOrderNo());
        } catch (Exception e) {
            resultMap.put("status", "500");
            resultMap.put("message", "支付宝预下单失败(追加)!!!");
            log.error("支付宝App支付(活体)：支付订单生成失败out_trade_no---- {}", order.getHtOrderNo());
        }
        return ResultResponse.createBySuccess(resultMap);
    }

    /**
     * 活体异步回调
     * @param params
     * @return
     */
    @Override
    public ResultResponse aliPayCallbackHT(Map<String, String> params) {

        String htOrderNo = String.valueOf(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        //正常订单
        HtOrderInfo order = htOrderInfoRepository.findByHtOrderNo(htOrderNo);
        if (order == null) {
            log.error("【支付回调】订单不存在:htOrderNo:{}", htOrderNo);
            return ResultResponse.createByErrorMessage("非该商铺的订单,回调忽略");
        }
        if (order.getOrderStatus() >= OrderStatusEnum.PAID.getStatus()) {
            log.error("【支付回调】支付宝重复调用: orderNo:{}", htOrderNo);
            return ResultResponse.createBySuccess("支付宝重复调用");
        }
        if (Const.AliPayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            htOrderInfoRepository.updateHtOrderInfoOrderStatus(htOrderNo);
            //推送短信到商家
            //推送短信到用户
            if (order.getReceiveId() != null) {
                UserAddress address = userAddressRepository.findById(order.getReceiveId()).get();
                smsService.sendNewOrderUser(htOrderNo, address.getPhone());
            }
            //推送短信到配送员
            if (order.getServiceType() != 3) {
                List<Express> expressList = expressRepository.findByAreaCodeAndStatus(order.getAreaCode(), 1);
                expressList.stream().forEach(express -> {
                    smsService.sendExpressNewOrder(htOrderNo, express.getPhone());
                });
            }
            //调用活体支付资金流转逻辑
            //TODO
        }
        //生成支付信息
        try {
            PayInfo payInfo = new PayInfo();
            payInfo.setUserId(order.getBuyerUserId());
            payInfo.setOrderNo(order.getHtOrderNo());
            payInfo.setPayPlatform(PayPlatformEnum.ALI_PAY.getCode());
            payInfo.setPlatformNumber(tradeNo);
            payInfo.setPlatformStatus(tradeStatus);
            payInfo.setType(1);
            payInfoRepository.save(payInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("【支付宝异步回调】支付信息生成失败: htOrderNo:{}", htOrderNo);
        }

        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse aliPayInsurance(Integer orderId, Integer userId) {
        InsuranceFeeRecord insuranceFeeRecord = insuranceFeeRecordRepository.findById(orderId).orElse(null);
        if(insuranceFeeRecord == null) {
            return ResultResponse.createByErrorMessage("无效的保险医疗订单ID");
        }
        if(insuranceFeeRecord.getStatus() > -1) {
            return ResultResponse.createByErrorMessage("该订单已支付，请勿重复支付");
        }
        String orderNo = insuranceFeeRecord.getOrderNo();
        String orderStr = "";
        Map<String, String> resultMap = new HashMap<>();
        try {
            //实例化客户端
            /*
         * 开放平台SDK封装了签名实现，只需在创建DefaultAlipayClient对象时，
         * 设置请求网关(gateway)，应用id(app_id)，应用私钥(private_key)，编码格式(charset)，支付宝公钥(alipay_public_key)，签名类型(sign_type)即可，
         * 报文请求时会自动进行签名
         * */
            AlipayClient client = new DefaultAlipayClient(AliPayConfig.GATEWAY, AliPayConfig.ALI_PAY_APPID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT,
                    AliPayConfig.CHARSET, AliPayConfig.ALI_PAY_PUBLIC_KEY, AliPayConfig.SIGN_TYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            String comment = insuranceFeeRecord.getComment();
            if(StringUtils.isBlank(comment)) {
                comment = "宠物医疗费用";
            }
            model.setSubject(comment);//商品名称
            model.setOutTradeNo(orderNo);//商户订单号
            model.setTimeoutExpress("120m"); //交易超时时间
            model.setTotalAmount(String.valueOf(insuranceFeeRecord.getMoney())); //支付金额
            model.setProductCode("FAST_INSTANT_TRADE_PAY"); //销售产品码
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AliPayConfig.NOTIFY_URL_INSURANCE); //App支付异步回调地址
            ali_request.setReturnUrl(AliPayConfig.RETURN_URL); //付款完成跳转页面
            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            orderStr = response.getBody();
            if (StringUtils.isBlank(orderStr)) {
                OrderLog orderLog = OrderLogDTO.addOrderLogInsurance(insuranceFeeRecord);
                orderLog.setNote("支付宝保险医疗预下单失败");
                logRepository.save(orderLog);
                return ResultResponse.createBySuccessMessage("支付宝保险医疗预下单失败!!!");
            }
            String orderStrResult = orderStr;
            resultMap.put("orderStr", orderStrResult);//就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("status", "200");
            resultMap.put("message", "支付宝保险医疗预下单成功");
            OrderLog orderLog = OrderLogDTO.addOrderLogInsurance(insuranceFeeRecord);
            orderLog.setNote("支付宝保险医疗预下单成功");
            orderLog.setOrderStatus(OrderStatusEnum.ORDER_PRE.getStatus());
            logRepository.save(orderLog);
            log.info("支付宝App支付:保险医疗支付订单创建成功out_trade_no---- {}", insuranceFeeRecord.getOrderNo());
        } catch (Exception e) {
            resultMap.put("status", "500");
            resultMap.put("message", "保险医疗支付宝预下单失败!!!");
            log.error("支付宝App支付：保险医疗支付订单生成失败out_trade_no---- {}", insuranceFeeRecord.getOrderNo());
        }
        return ResultResponse.createBySuccess(resultMap);
    }

    @Override
    public ResultResponse aliPayCallbackInsurance(Map<String, String> params) {
        String orderNo = String.valueOf(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        List<InsuranceFeeRecord> list = insuranceFeeRecordRepository.findByOrderNo(orderNo);
        InsuranceFeeRecord ifr = list.get(0);
        System.out.println("orderNo:" + orderNo);
        System.out.println("tradeNo:" + tradeNo);
        // 医疗费用订单
        if (Const.AliPayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            List<InsuranceFeeRecord> ifrs = insuranceFeeRecordRepository.findByOrderNo(orderNo);
            if (ifrs.size() > 0) {
                InsuranceFeeRecord insuranceFeeRecord = ifrs.get(0);
                insuranceFeeRecord.setStatus(1);
                insuranceFeeRecord.setPaymentTime(new Date());
                insuranceFeeRecord.setPaymentType(PayPlatformEnum.WX_APP_PAY.getCode());
                InsuranceFeeRecord save = insuranceFeeRecordRepository.save(insuranceFeeRecord);
                //生成支付信息
                successCallBackPayInfoOperate(save.getUserId(), save.getOrderNo(), "", "", "", PayPlatformEnum.ALI_PAY.getCode());
                //发送短消息
                successCallBackMsgInsuranceOrderOperate(save);
                //调用医疗订单资金流转逻辑
                cashAccountService.insuranceFeeCashIn(insuranceFeeRecord);
                //生成支付凭证
                insuranceFeeRecordService.generateInsuranceFeeRecordCertificate(insuranceFeeRecord.getId());
            }
        }
        //生成支付信息
        try {
            PayInfo payInfo = new PayInfo();
            payInfo.setUserId(ifr.getUserId());
            payInfo.setOrderNo(ifr.getOrderNo());
            payInfo.setPayPlatform(PayPlatformEnum.ALI_PAY.getCode());
            payInfo.setPlatformNumber(tradeNo);
            payInfo.setPlatformStatus(tradeStatus);
            payInfo.setType(3);
            payInfoRepository.save(payInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("【支付宝异步回调】支付信息生成失败: orderNo:{}", orderNo);
        }
        return ResultResponse.createBySuccess();
    }


    /**
     * 支付宝普通订单异步回调
     *
     * @param params
     * @return
     */
    @Override
    public ResultResponse aliCallback(Map<String, String> params) {
        String orderNo = String.valueOf(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        OrderInfo order = null;
        if (orderNo.contains("RE")) {
            //追加订单
            OrderInfoRe orderInfoRe = orderInfoReRepository.findByReOrderNo(orderNo);
            if (orderInfoRe.getStatus() == 0) {
                return ResultResponse.createBySuccess("支付宝重复调用");
            }
            if (Const.AliPayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
//                orderInfoRe.setId(orderInfoRe.getId());
                orderInfoRe.setPayTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
                orderInfoRe.setStatus(0);
                //销量更新
                List<OrderDetail> orderDetailList = orderDetailRepository.findByUserIdAndReOrderNo(orderInfoRe.getUserId(), orderInfoRe.getReOrderNo());
                orderDetailList.stream().forEach(orderDetail -> {
                    goodsRepository.updateGoodIdIn(orderDetail.getCount(), orderDetail.getGoodId());
                });
                orderInfoReRepository.save(orderInfoRe);
                //生成支付信息
                try {
                    PayInfo payInfo = new PayInfo();
                    payInfo.setUserId(order.getUserId());
                    payInfo.setOrderNo(order.getOrderNo());
                    payInfo.setOrderReNo(orderInfoRe.getReOrderNo());
                    payInfo.setPayPlatform(PayPlatformEnum.ALI_PAY.getCode());
                    payInfo.setPlatformNumber(tradeNo);
                    payInfo.setPlatformStatus(tradeStatus);
                    payInfo.setType(3);
                    payInfoRepository.save(payInfo);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("【支付宝异步回调】支付信息生成失败: orderNo:{}", orderNo);
                }
                //调用追加订单资金流转程序
                //TODO
            }
        } else {
            //正常订单
            order = orderInfoMapper.selectByOrderNo(orderNo);
            if (order == null) {
                log.error("【支付回调】订单不存在:orderNo:{}", orderNo);
                return ResultResponse.createByErrorMessage("非该商铺的订单,回调忽略");
            }
            if (order.getOrderStatus() >= OrderStatusEnum.PAID.getStatus()) {
                log.error("【支付回调】支付宝重复调用: orderNo:{}", orderNo);
                return ResultResponse.createBySuccess("支付宝重复调用");
            }
            if (Const.AliPayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
                //如果用户下单使用优惠券，需要将该优惠券删除或数量减少
                // TODO: 2019-10-22  
                order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
                order.setOrderStatus(OrderStatusEnum.PAID.getStatus());
                order.setPaymentType(PaymentTypeEnum.ALI_PAY.getStatus());
                //销量更新
                List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderNo(orderNo);
                if (!CollectionUtils.isEmpty(orderDetailList)) {
                    orderDetailList.stream().forEach(orderDetail -> {
                        goodsRepository.updateGoodIdIn(orderDetail.getCount(), orderDetail.getGoodId());
                    });
                }
                //优惠券减少(配送)
                couponCommon.decrCouponCount(order);
                orderInfoMapper.updateByPrimaryKeySelective(order);
                //判断是否自动接单
                Shop shop = shopRepository.findById(order.getShopId()).get();
                if (shop.getIsAutoAccept() != null && shop.getIsAutoAccept() == 1) {
                    //推送短信到商家
                    this.sendSmsShop(shop,orderDetailList,orderNo);
                    //推送短信到用户
                    if (order.getReceiveAddressId() != null) {
                        UserAddress address = userAddressRepository.findById(order.getReceiveAddressId()).get();
                        smsService.sendNewOrderUser(orderNo, address.getPhone());
                    }
                    //推送短信到配送员
                    if (order.getServiceType() != 3) {
                        List<Express> expressList = expressRepository.findByAreaCodeAndStatus(shop.getAreaCode(), 1);
                        expressList.stream().forEach(express -> {
                            smsService.sendExpressNewOrder(orderNo, express.getPhone());
                        });
                    }
                    //调用商家接单方法
                    orderService.shopAcceptOrder(order.getId());
                } else {
                    //通知商家接单
                    //TODO
                }
                //调用用户支付后的资金流转逻辑
                cashAccountService.customOrderCashIn(order);
                //调用返利方法
                recommendOrder(order);
            }
            //生成支付信息
            try {
                PayInfo payInfo = new PayInfo();
                payInfo.setUserId(order.getUserId());
                payInfo.setOrderNo(order.getOrderNo());
                payInfo.setPayPlatform(PayPlatformEnum.ALI_PAY.getCode());
                payInfo.setPlatformNumber(tradeNo);
                payInfo.setPlatformStatus(tradeStatus);
                payInfo.setType(1);
                payInfoRepository.save(payInfo);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("【支付宝异步回调】支付信息生成失败: orderNo:{}", orderNo);
            }
        }
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse wxPay(HttpServletRequest req, String orderNo, Integer totalFee, String goodStr, Integer payType) {
        WxUnifiedorderModelDTO model = new WxUnifiedorderModelDTO();
        if (payType == 1) {
            //app应用appId
            model.setAppid(StringUtils.trim(BasicInfo.APP_AppID));
            //交易类型/请求方式
            model.setTrade_type("APP");
        } else if (payType == 2) {
            model.setAppid(StringUtils.trim(BasicInfo.xcxID));
            //交易类型/请求方式
            model.setTrade_type("JSAPI");
        }
        //商户号
        model.setMch_id(StringUtils.trim(BasicInfo.APP_MchId));
        //随机字符串
        String noncestr = PayUtil.getRandomStr();
        model.setNonce_str(noncestr);
        //加密方式
        model.setSign_type("MD5");
        //商品描述 body
        if (goodStr == null || goodStr.equals("")) {
            goodStr = "标准商品";
        }
        model.setBody(BasicInfo.APP_NAME + "-" + goodStr);
        //支付订单号
        model.setOut_trade_no(orderNo);
        System.out.println("订单号:" + orderNo);

        //如果为保险医疗费用订单查出该订单需支付的费用, 并转换成分
        if(totalFee == null) {
            if(orderNo.indexOf("IFR") != -1) {
                List<InsuranceFeeRecord> list = insuranceFeeRecordRepository.findByOrderNo(orderNo);
                if(list != null && list.size() > 0) {
                    InsuranceFeeRecord insuranceFeeRecord = list.get(0);
                    totalFee = insuranceFeeRecord.getMoney().multiply(new BigDecimal(100)).intValue();
                }
            }
        }
        //支付金额
        model.setTotal_fee(totalFee);
        //我们服务器的IP
        model.setSpbill_create_ip(BasicInfo.SERVER_IP);
        //异步通知地址
        model.setNotify_url(BasicInfo.NotifyUrl);
        //签名
        model.setSign(SignUtil.sign(SignUtil.createUnifiedSign(model), BasicInfo.APP_MchKey));

        try {
            XStream s = new XStream(new DomDriver());
            s.alias("xml", model.getClass());
            String xml = s.toXML(model);
            xml = xml.replace("__", "_");
            String response = PayUtil.ssl(BasicInfo.unifiedordersurl, xml, req, BasicInfo.APP_MchId);
            WxUnifiedorderResponseDTO ret = (WxUnifiedorderResponseDTO) fromXML(response, WxUnifiedorderResponseDTO.class);

            if (ret != null) {
                System.out.println("-------------------");
                System.out.println(ret.toString());
            }

            if ("SUCCESS".equals(ret.getResult_code())) {
                //再次签名
                Map<String, String> finalpackage = new TreeMap<>();
                String timestamp = (System.currentTimeMillis()/1000) + "";
                if (payType == 1) {
                    //app支付
                    finalpackage.put("appid", BasicInfo.APP_AppID);
                } else if (payType == 2) {
                    //xcx支付
                    finalpackage.put("appid", BasicInfo.xcxID);
                }
                finalpackage.put("timestamp", timestamp);
                finalpackage.put("noncestr", noncestr);
                finalpackage.put("prepayid", ret.getPrepay_id());
                finalpackage.put("package", "Sign=WXPay");
                finalpackage.put("partnerid", BasicInfo.APP_MchId);

                String sign = SignUtil.sign(finalpackage, BasicInfo.APP_MchKey);

                ret.setSign(sign);
                ret.setNonce_str(noncestr);
                ret.setTimestamp(timestamp);

                //生成orderLog记录
                unifiedOrderCallback(orderNo);

                //将预支付信息返回前端, 由前端调起微信支付
                return ResultResponse.createBySuccess(ret);
            } else {
//                log.error("微信下单失败》》" + "错误码:" + ret.getReturn_code() + "  ;" + "描述:" + ret.getReturn_msg());
//                return ResultResponse.createBySuccess("微信下单失败》》" + "错误码:" + ret.getReturn_code() + "  ;" + "描述:" + ret.getReturn_msg());
                log.error("微信下单失败》》" + "返回错误码:" + ret.getErr_code() + "  ;" + "描述:" + ret.getErr_code_des());
                return ResultResponse.createBySuccess("微信下单失败》》" + "返回错误码:" + ret.getErr_code() + "  ;" + "描述:" + ret.getErr_code_des());
            }
        } catch (Exception e) {
            log.error("微信下单异常》》" + e);
            return ResultResponse.createBySuccess("微信下单异常》》" + e);
        }
    }

    private Object fromXML(String xml, Class<?> objClass) {
        Serializer serializer = new Persister();
        try {
            return serializer.read(objClass, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultResponse wxPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream is = request.getInputStream();
        HashMap<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(is);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        String out_trade_no;//订单ID
        String total_fee;   //订单金额
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        // 获取微信返回值信息
        for (Element e : list) {
            map.put(e.getName().trim(), e.getText().trim());
            if (e.getName().trim().equals("out_trade_no")) {
                out_trade_no = e.getText().trim();
                System.out.println("out_trade_no的值是：---" + out_trade_no + "，当前方法=PayController.wxPayCallback()");
            } else if (e.getName().trim().equals("cash_fee")) {
                total_fee = e.getText().trim();
                System.out.println("total_fee的值是：---" + total_fee + "，当前方法=PayController.wxPayCallback()");
            }
        }
        is.close();

        // 克隆传入的信息并进行验签，建议一定要验证签名，防止返回值被篡改
        HashMap<String, String> signMap = (HashMap<String, String>) map.clone();
        signMap.remove("sign");
        // 这里的wx_key 是用户自定义的支付key
        String key = BasicInfo.MchKey;
        String sign = SignUtil.sign(signMap, key);

        if (!sign.equals(map.get("sign"))) {
            //返回签名不一致
            return ResultResponse.createByErrorMessage("支付失败, 签名不一致!");
        }
        // 信息处理
        String result_code = map.get("result_code");
        try {
            if ("SUCCESS".equals(result_code)) {
                //由于微信后台会同时回调多次，所以需要做防止重复提交操作的判断
                //此处放防止重复提交操作
            } else if ("FAIL".equals(result_code)) {
                return ResultResponse.createByErrorMessage("支付失败, 微信回调失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //回调报错
            return ResultResponse.createByErrorMessage("支付失败, 微信回调报错!");
        }

        //这里是验证返回值没问题了，可以写具体的支付成功的逻辑
        wxPayCallBackGenerateOrder(list);

        // 返回信息，防止微信重复发送报文
        String result = "<xml>"
                + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.print(result);
        out.flush();
        out.close();

        // 返回成功信息给前台
        return ResultResponse.createBySuccessMessage("微信支付成功!");
    }


    /**
     * 统一下单成功后处理逻辑
     */
    private void unifiedOrderCallback(String orderNo) {
        if(orderNo.indexOf("IFR") != -1) {
            //医疗费用
            List<InsuranceFeeRecord> list = insuranceFeeRecordRepository.findByOrderNo(orderNo);
            if(list == null || list.size() == 0) {
                System.out.println("无效的保险医疗订单号(orderNo)");
                return;
            }
            InsuranceFeeRecord ifr = list.get(0);
            OrderLog orderLog = OrderLogDTO.addOrderLogInsurance(ifr);
            orderLog.setNote("微信保险医疗预下单成功");
            orderLog.setOrderStatus(OrderStatusEnum.ORDER_PRE.getStatus());
            logRepository.save(orderLog);
        } else {
            OrderInfo order = orderInfoRepository.findByOrderNo(orderNo);
            if(order == null) {
                System.out.println("无效的订单号(orderNo)");
                return;
            }
            OrderLog orderLog = OrderLogDTO.addOrderLog(order);
            orderLog.setNote("微信保险医疗预下单成功");
            orderLog.setOrderStatus(OrderStatusEnum.ORDER_PRE.getStatus());
            logRepository.save(orderLog);
        }
    }

    /**
     * 支付成功后处理逻辑
     */
    private void wxPayCallBackGenerateOrder(List<Element> list) {
        String orderNo = getCallbackInfoByName(list, "out_trade_no");
        Date time = DateTimeUtil.strToDate(getCallbackInfoByName(list, "time_end"));
        String transactionId = getCallbackInfoByName(list, "transaction_id");
        String resultCode = getCallbackInfoByName(list, "result_code");
        if (orderNo.indexOf("RE") != -1) {
            //追加订单
            OrderInfoRe re = orderInfoReRepository.findByReOrderNo(orderNo);
            re.setPayTime(time);
            re.setStatus(0);
            //销量更新
            List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderNoUserId(re.getOrderNo(), re.getUserId());
            orderDetailList.stream().forEach(orderDetail -> {
                goodsRepository.updateGoodIdIn(orderDetail.getCount(), orderDetail.getGoodId());
            });
            OrderInfoRe save = orderInfoReRepository.save(re);
            //生成支付信息
            successCallBackPayInfoOperate(save.getUserId(), save.getOrderNo(), save.getReOrderNo(), transactionId, resultCode, PayPlatformEnum.WX_APP_PAY.getCode());
            //发送短消息
            successCallBackMsgReOrderOperate(save);
            //调用追加订单资金流转逻辑
            //TODO
        } else if (orderNo.indexOf("IFR") != -1) {
            List<InsuranceFeeRecord> ifrs = insuranceFeeRecordRepository.findByOrderNo(orderNo);
            if (ifrs.size() > 0) {
                InsuranceFeeRecord insuranceFeeRecord = ifrs.get(0);
                insuranceFeeRecord.setStatus(1);
                insuranceFeeRecord.setPaymentTime(new Date());
                insuranceFeeRecord.setPaymentType(PayPlatformEnum.WX_APP_PAY.getCode());
                InsuranceFeeRecord save = insuranceFeeRecordRepository.save(insuranceFeeRecord);
                //生成支付信息
                successCallBackPayInfoOperate(save.getUserId(), save.getOrderNo(), "", transactionId, resultCode, PayPlatformEnum.WX_APP_PAY.getCode());
                //发送短消息
                successCallBackMsgInsuranceOrderOperate(save);
                //调用医疗订单资金流转逻辑
                cashAccountService.insuranceFeeCashIn(insuranceFeeRecord);
                //生成支付凭证
                insuranceFeeRecordService.generateInsuranceFeeRecordCertificate(insuranceFeeRecord.getId());
            }
        } else {
            OrderInfo order = orderInfoRepository.findByOrderNo(orderNo);
            order.setPaymentTime(time);
            order.setOrderStatus(OrderStatusEnum.PAID.getStatus());
            order.setPaymentType(PaymentTypeEnum.WX_APP_PAY.getStatus());
            //销量更新
            List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderNo(orderNo);
            orderDetailList.stream().forEach(orderDetail -> {
                goodsRepository.updateGoodIdIn(orderDetail.getCount(), orderDetail.getGoodId());
            });
            //生成支付信息
            successCallBackPayInfoOperate(order.getUserId(), order.getOrderNo(), "", transactionId, resultCode, PayPlatformEnum.WX_APP_PAY.getCode());
            orderInfoMapper.updateByPrimaryKeySelective(order);
            //发送短信息
            successCallBackMsgoOrderOperate(order);
            //调用用户支付后的资金流转逻辑
            cashAccountService.customOrderCashIn(order);
            //调用订单返利
            recommendOrder(order);
            //生成流转记录
            orderOperateLogService.addOrderOperateLogService(order.getId(), order.getOrderNo(), "", OrderStatusEnum.NO_PAY.getStatus(), OrderStatusEnum.PAID.getStatus());
        }
    }

    /**
     * 订单返利
     * @param orderInfo
     */
    private void recommendOrder(OrderInfo orderInfo) {
        boolean flag = recommendService.isSatisfyOrderRewardQualificationByOrderNo(orderInfo.getOrderNo());//校验是否满足订单返利功能
        if(flag) {
            recommendService.recommendUserFirstOrder(orderInfo.getId());
        }
    }

    /**
     * 成功回调之后的支付信息处理
     *
     * @param userId
     * @param orderNo
     * @param orderReNo
     * @param transactionId
     * @param resultCode
     * @param payCode
     */
    private void successCallBackPayInfoOperate(Integer userId, String orderNo, String orderReNo, String transactionId, String resultCode, Integer payCode) {
        //生成支付信息
        try {
            PayInfo payInfo = new PayInfo();
            payInfo.setUserId(userId);
            payInfo.setOrderNo(orderNo);
            if (StringUtils.isNotBlank(orderReNo)) {
                payInfo.setOrderReNo(orderReNo);
            }
            payInfo.setPayPlatform(payCode);
//            payInfo.setPlatformNumber(transactionId);
//            payInfo.setPlatformStatus(resultCode);
            if(StringUtils.isNotBlank(orderReNo)) {
                //追加订单
                payInfo.setType(2);
            } else if(StringUtils.isNotBlank(orderNo)){
                if(orderNo.indexOf("IFR") != -1) {
                    //医疗费用订单
                    payInfo.setType(3);
                } else {
                    //正常订单
                    payInfo.setType(1);
                }
            }
            payInfoRepository.save(payInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("支付信息生成失败: orderNo:{}", orderNo);
        }
    }

    /**
     * 成功回调之后的短信处理(正常订单)
     *
     * @param order
     */
    private void successCallBackMsgoOrderOperate(OrderInfo order) {
        //判断是否自动接单
        Shop shop = shopRepository.findById(order.getShopId()).get();
        if (shop.getIsAutoAccept() != null && shop.getIsAutoAccept() == 1) {
            //推送短信到商家
            smsService.sendOrderAutoAcceptShop(order.getOrderNo(), shop.getPhone());
            //推送短信到用户
            if (order.getReceiveAddressId() != null) {
                UserAddress address = userAddressRepository.findById(order.getReceiveAddressId()).get();
                smsService.sendNewOrderUser(order.getOrderNo(), address.getPhone());
            }
            //推送短信到配送员
            List<Express> expressList = expressRepository.findByAreaCodeAndStatus(shop.getAreaCode(), 1);
            expressList.stream().forEach(express -> {
                smsService.sendExpressNewOrder(order.getOrderNo(), express.getPhone());
            });
            //调用商家接单逻辑
            orderService.shopAcceptOrder(order.getId());
        } else {
            //通知商家接单
        }
    }

    /**
     * 成功回调之后的短信处理(医疗费用订单)
     *
     * @param insuranceFeeRecord
     */
    private void successCallBackMsgInsuranceOrderOperate(InsuranceFeeRecord insuranceFeeRecord) {

    }

    /**
     * 成功回调之后的短信处理(追加订单)
     *
     * @param orderInfoRe
     */
    private void successCallBackMsgReOrderOperate(OrderInfoRe orderInfoRe) {

    }

    private String getCallbackInfoByName(List<Element> list, String name) {
        String r = "";
        for (Element e : list) {
            if (e.getName().trim().equals(name)) {
                r = e.getText().trim();
            }
        }
        return r;
    }

    /**
     * 根据条件推送短信到店铺
     * @param shop
     * @param orderDetailList
     * @param orderNo
     */
    private void sendSmsShop(Shop shop, List<OrderDetail> orderDetailList,String orderNo){
        //推送短信到商家（如果shopId等于1代表是官方店铺上传的商品，需要派单给5公里内的商家（商家需包含该商品））
        if (shop.getId() == 1 && !CollectionUtils.isEmpty(orderDetailList)) {
            orderDetailList.stream().forEach(orderDetail -> {
                Good good = goodsRepository.findById(orderDetail.getGoodId()).orElse(null);
                if (good != null) {
                    List<Shop> shopList = shopMapper.findShopList(shop.getLng(), shop.getLat(), shop.getAreaCode(), good.getShopIds());
                    if (!CollectionUtils.isEmpty(shopList)) {
                        shopList.stream().forEach(s -> {
                            if (s.getId() != 1) {
                                smsService.sendOrderAutoAcceptShop(orderNo, s.getPhone());
                            }
                        });
                    }
                }
            });
        }else {
            smsService.sendOrderAutoAcceptShop(orderNo, shop.getPhone());
        }
    }

    /**
     * 清空购物车
     *
     * @param cartList
     */
    private void cleanCart(List<Carts> cartList) {
        for (Carts cart : cartList) {
            cartsMapper.deleteByPrimaryKey(cart.getId());
        }
    }



}
