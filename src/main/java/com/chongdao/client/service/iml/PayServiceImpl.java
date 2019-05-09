package com.chongdao.client.service.iml;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.config.AliPayConfig;
import com.chongdao.client.dto.WxUnifiedorderModelDTO;
import com.chongdao.client.dto.WxUnifiedorderResponseDTO;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
            List<OrderDetail> orderItemList = orderDetailMapper.getByOrderNoUserId(order.getOrderNo(), order.getUserId());
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
            if (StringUtils.isBlank(orderStr)) {
                return ResultResponse.createBySuccessMessage("支付宝预下单失败!!!");
            }
            resultMap.put("orderStr", orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("status", "200");
            resultMap.put("message", "支付宝预下单成功");
            log.info("支付宝App支付:支付订单创建成功out_trade_no----", order.getOrderNo());
        } catch (Exception e) {
            resultMap.put("status", "500");
            resultMap.put("message", "支付宝预下单失败!!!");
            log.error("支付宝App支付：支付订单生成失败out_trade_no----", order.getOrderNo());
        }
        return ResultResponse.createBySuccess(resultMap);
    }

    /**
     * 支付宝异步回调
     *
     * @param params
     * @return
     */
    @Override
    public ResultResponse aliCallback(Map<String, String> params) {
        String orderNo = String.valueOf(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        OrderInfo order = orderInfoMapper.selectByOrderNo(orderNo);
        if (order == null) {
            log.error("【支付回调】订单不存在:orderNo:{}", orderNo);
            return ResultResponse.createByErrorMessage("非该商铺的订单,回调忽略");
        }
        if (order.getOrderStatus() >= OrderStatusEnum.PAID.getStatus()) {
            log.error("【支付回调】支付宝重复调用: orderNo:{}", orderNo);
            return ResultResponse.createBySuccess("支付宝重复调用");
        }
        if (Const.AliPayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
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

    @Override
    public ResultResponse wxPay(HttpServletRequest req, String orderNo, Integer totalFee, String goodStr) {
        WxUnifiedorderModelDTO model = new WxUnifiedorderModelDTO();
        //app应用appId
        model.setAppid(BasicInfo.APP_AppID);
        //商户号
        model.setMch_id(BasicInfo.APP_MchId);
        //随机字符串
        String noncestr = PayUtil.getRandomStr();
        model.setNonce_str(noncestr);
        //签名
        model.setSign(SignUtil.sign(SignUtil.createUnifiedSign(model), BasicInfo.APP_MchKey));
        //加密方式
        model.setSign_type("MD5");
        //商品描述 body
        if (goodStr == null || goodStr.equals("")) {
            goodStr = "标准商品";
        }
        model.setBody(BasicInfo.APP_NAME + "-" + goodStr);
        //支付订单号
        model.setOut_trade_no(orderNo);
        //支付金额
        model.setTotal_fee(totalFee);
        //我们服务器的IP
        model.setSpbill_create_ip(BasicInfo.SERVER_IP);
        //异步通知地址
        model.setNotify_url(BasicInfo.NotifyUrl);
        //交易类型/请求方式
        model.setTrade_type("APP");

        try {
            XStream s = new XStream(new DomDriver());
            s.alias("xml", model.getClass());
            String xml = s.toXML(model);
            String response = PayUtil.ssl(BasicInfo.unifiedordersurl, xml, req, BasicInfo.APP_MchId);
            WxUnifiedorderResponseDTO ret = (WxUnifiedorderResponseDTO) fromXML(response, WxUnifiedorderResponseDTO.class);

            if (ret != null) {
                System.out.println("-------------------");
                System.out.println(ret.toString());
            }

            if ("SUCCESS".equals(ret.getResult_code())) {
                //再次签名
                Map<String, String> finalpackage = new TreeMap<>();
                String timestamp = (System.currentTimeMillis() / 1000) + "";
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

                //将预支付信息返回前端, 由前端调起微信支付
                return ResultResponse.createBySuccess(ret);
            } else {
                log.error("微信下单失败》》" + "错误码:" + ret.getReturn_code() + "  ;" + "描述:" + ret.getReturn_msg());
                return ResultResponse.createBySuccess("微信下单失败》》" + "错误码:" + ret.getReturn_code() + "  ;" + "描述:" + ret.getReturn_msg());
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

            return null;
        }
        // 信息处理
        String result_code = map.get("result_code");
        try {
            if ("SUCCESS".equals(result_code)) {
                //由于微信后台会同时回调多次，所以需要做防止重复提交操作的判断
                //此处放防止重复提交操作
            } else if ("FAIL".equals(result_code)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            //回调报错

            return null;
        }

        //这里是验证返回值没问题了，可以写具体的支付成功的逻辑


        // 返回信息，防止微信重复发送报文
        String result = "<xml>"
                + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.print(result);
        out.flush();
        out.close();
        return null;
    }
}
