package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.util.List;

/**
 * 短信通知服务
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

    void customOrderMsgSenderSimple(String msg, String shopName, String orderNo, String phone);

    void customOrderMsgSenderSimpleNoShopName(String msg, String orderNo, String phone);

    void customOrderMsgSenderPatch(String msg, String shopName, String orderNo, List<String> phoneList);

    void customOrderMsgSenderPatchNoShopName(String msg, String orderNo, List<String> phoneList);

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
     * 获取商家联系号码
     * @param orderId
     * @return
     */
    String getShopPhoneByOrderId(Integer orderId);

    /**
     * 获取订单联系人号码列表
     * @param orderId
     * @return
     */
    List<String> getUserPhoneListByOrderId(Integer orderId);
}
