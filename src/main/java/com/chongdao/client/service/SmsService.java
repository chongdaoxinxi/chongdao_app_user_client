package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.util.List;
import java.util.Map;

/**
 * 验证码服务
 */
public interface SmsService {

    /**
     * 发送验证码到指定手机 并 缓存验证码 10分钟 及 请求间隔时间1分钟
     * @param telephone
     * @return
     */
    ResultResponse<String> sendSms(String telephone);

    /**
     * 获取缓存中的验证码
     * @param telephone
     * @return
     */
    String getSmsCode(String telephone);

    /**
     * 移除指定手机号的验证码缓存
     */
    void remove(String telephone);

    /**
     * 商家接单-用户短信通知
     * @param orderNo
     * @param shopName
     * @param phoneList
     */
    void acceptOrderMsgUserSender(String orderNo, String shopName, List<String> phoneList);

    /**
     * 商家接单-配送员短信通知
     * @param orderNo
     * @param shopName
     * @param phoneList
     */
    void acceptOrderMsgExpressSender(String orderNo, String shopName, List<String> phoneList);

    /**
     * 商家接单-店铺短信通知
     * @param orderNo
     * @param shopName
     * @param telephone
     */
    void acceptOrderMsgShopSender(String orderNo, String shopName, String telephone);

    /**
     * 商家同意退款-管理员通知
     * @param orderNo
     * @param shopName
     * @param phoneList
     */
    void refundOrderMsgAdminSender(String orderNo, String shopName, List<String> phoneList);

    /**
     * 商家拒单-用户通知
     * @param orderNo
     * @param shopName
     * @param telephone
     */
    void refuseOrderMsgUserSender(String orderNo, String shopName, String telephone);

    /**
     * 商家服务完成-配送员通知
     * @param orderNo
     * @param shopName
     * @param telephone
     */
    void serviceCompleteMsgExpressSender(String orderNo, String shopName, String telephone);

    /**
     * 商家服务完成-用户通知
     * @param orderNo
     * @param shopName
     * @param phoneList
     */
    void serviceCompleteMsgUserSender(String orderNo, String shopName, List<String> phoneList);

    /**
     * 获取订单所在地区的配送员号码列表
     * @param orderId
     * @return
     */
    List<String> getExpressPhoneListByOrderId(Integer orderId);

    /**
     * 获取负责订单的配送员号码
     * @param orderId
     * @return
     */
    String getExpressPhoneByOrderId(Integer orderId);

    /**
     * 获取订单所在地区的管理员号码列表
     * @param orderId
     * @return
     */
    List<String> getAdminPhoneListByOrderId(Integer orderId);

    /**
     * 获取商铺信息参数(名称, 联系号码)
     * @param orderId
     * @return
     */
    Map<String, String> getShopParamsByOrderId(Integer orderId);

    /**
     * 获取订单的用户信息参数(接:联系人, 接:电话, 送:联系人, 送:联系电话)
     * @param orderId
     * @return
     */
    Map<String, String> getUserParamsByOrderId(Integer orderId);
}
