package com.chongdao.client.utils.wxpay;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
public class BasicInfo {
    /**微信账号信息配置**/

    /**
     * 公众号appId
     **/
    public static final String appID = "";
    /**
     * 公众号appSecret
     **/
    public static final String AppSecret = "";
    /**
     * 公众号商户号
     **/
    public static final String MchId = "";
    /**
     * 公众号商户号秘钥
     **/
    public static final String MchKey = "";

    /***app应用appId***/
    public static final String APP_AppID = "";
    /***app应用appSecret***/
    public static final String APP_AppSecret = "";
    /***app应用商户号***/
    public static final String APP_MchId = "";
    /***app应用商户密钥***/
    public static final String APP_MchKey = "";
    /**
     * 应用服务器IP
     */
    public static final String SERVER_IP = "47.101.160.88";
    /**
     * 应用名称(应用市场中的名称)
     */
    public static final String APP_NAME="养宠有道";
    /**
     * 商户证书路径
     **/
    public static final String KeyPath = "/apiclient_cert.p12";

    //微信回调URL
    public static final String NotifyUrl = "http://39.108.83.186/api/callBackController/wx_pay_callback";

    //微信统一下单接口
    public static final String unifiedordersurl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
