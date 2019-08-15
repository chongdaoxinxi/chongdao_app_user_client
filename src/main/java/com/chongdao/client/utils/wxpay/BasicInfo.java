package com.chongdao.client.utils.wxpay;

/**
 * @Description 微信账号信息配置
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
public class BasicInfo {
    /**微信账号信息配置**/

    public static final String xcxID = "wx601e284d89b5ad6b";

    /**
     * 公众号appId
     **/
    public static final String appID = "wxd69bd4ce033aca70";
    /**
     * 公众号appSecret
     **/
    public static final String AppSecret = "437dd549567811195afa051d3f49327a";
    /**
     * 公众号商户号
     **/
    public static final String MchId = "1513194061";
    /**
     * 公众号商户号秘钥
     **/
    public static final String MchKey = "95bc30c8e25484485b53b998bace1d1c";

    /***app应用appId***/
    public static final String APP_AppID = "wxa194b8978445c264";
    /***app应用appSecret***/
    public static final String APP_AppSecret = "dc5c74d83f7baea40142877629fd1624";
    /***app应用商户号***/
    public static final String APP_MchId = "1513194061";
    /***app应用商户密钥***/
    public static final String APP_MchKey = "95bc30c8e25484485b53b998bace1d1c";
    /**
     * 应用服务器IP
     */
    public static final String SERVER_IP = "47.100.63.167";
    /**
     * 应用名称(应用市场中的名称)
     */
    public static final String APP_NAME="养宠有道";
    /**
     * 商户证书路径
     **/
    public static final String KeyPath = "/apiclient_cert.p12";

    //微信回调URL
    public static final String NotifyUrl = "http://47.100.63.167/app_client/api/callBackController/wx_pay_callback";

    //微信统一下单接口
    public static final String unifiedordersurl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
