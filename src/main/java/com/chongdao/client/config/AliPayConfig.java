package com.chongdao.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AliPayConfig {

    public static final String ALI_PAY_APPID = "2016093000631040"; // appid

    public static final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCYiBeQJQ/f3qXeVesW+pFIULmQAkKRFPpxnzJxNXrXralUx6VtW27sS40U3Tv02jc6a8UhhaoJiNtQPnAvj2PPsCYyGDRReQRj8swXV0GZU2/TuR+yh1xQ5WMuZQxqy/HPXkMsYVNsqmNPFj0k0pyI+zshQPpCt8AMpBIyGYorZoE6r2PWh3n9BogHXRrUhG7Kk3wJsx0iPbqF7+SAf8kXGuVhbrR70XxJbjkRqlthJeOEfRYFDfGF2vuEC8fDlqD1YMhLDm8sBsUMJJhnYsGX62Fx1lB7uMQ08QjbLET0OlZ7avz2AWI+FpWdVImm29Fr68jfOONVIpq77E/UT+PXAgMBAAECggEAa/y9oeF7UaVRjQ0K+SPAQ5BTL29KLWjKvjJVBe004Qkjvs/xgHChIU2Dvme6kOkE/Klb0RXOgh2QF0l9J06+/UTaZdDZCW1B/HjioQu3d5Oosh54IG7Qnr0n8Kq9lcZ5lbRInlzxMReYY199PlqVVC15eVuFUkLBBrIJ58pJFdvKp5VpEfcUyGhVD/OEonMtB3SRiXYLrA8/KkfK6cRuoeBjTX5OPZqxzmCbJBx6yZKswPokcCEjhqIn1nNPvNCqj7jNIpBbuoQ7ZL8tj4Lfb4FPMi2OaA1uazoDYdbyhX4Dj+/pCZe7I1Swvu57h8620iCwK1u9aX5ZI7sSj3YI4QKBgQDpXrYJ37QAT8LPzbxJXaDQCWwY3l0FBjm5PvUjdNSXn34HlDPUVYTIU42sExLdhWmnyuXMc1rMrT/Ze9YqO0oDk7F5XaVxE8NLgDaD590R0KXHgvhJWlYXWl00fa4PNKb3vIPY27BYrL7LXY2RDW1O8ix32s75mwMpZxB8/E+L+QKBgQCnUpxKPie77vuK+u92DQ3McmCkbguArRZQ9ptLjDrHJMouM/qIPuFegse/MEXzCcxU3VE1JFWrL9x1ERjmv6jQts0K9J5mb7+6mnLn+rP77basMuHz/Kal4Ee31GG/saWHH3YxXpuQPlmWm4xW/aOHeEKkUeQCtBDiVDqAf3nCTwKBgE102ga88QmJb6d5KZ1QLifeeLpoU4dvYYlSlBqIlGce0L3DOtCm8cNruAH88q4DseW3Oj55qxF0D1Un/RVXSGGXi7g0lMzLOm68x1tQgdd/4QYvOileimSF4ZmifTsexqxDLZ+0ivtjmo/1JR+XzUf+qTO+tCO0h4AebSKOu/hBAoGARu7PkO3OnG5QtvdwYqRgECcA71QzjiZSipow9ZMKK/hdub6RXAcENbPNiDCnxsOFmwiD77pqkvrkQ8CuxMvFfk2wmQ9gw15mcm4dfliaypdY1KFxhqS55SIyZS0lD0X5PVJKId3QpxK3mhBxK0O1eq2x2sVYmwjJC5nXfzgLXyMCgYBrhpmaE6iSf5ftNhhxadmvQ3AXXOZDVX2oRHjjjdQTOdTraBMdxoFwGzcUqRPAwDSWPHk0NooIVyeFU1VA6CDREIgzp9wlTZ9WZ+bSYEhO9l1Hfw9+mjeoYrB06ZNkVF1V9o2OMFk/PBgblnxZGA/5t1Pg8P3OO2Vw9GkQ3/cWmA=="; // app支付私钥

    public static final String ALI_PAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmIgXkCUP396l3lXrFvqRSFC5kAJCkRT6cZ8ycTV6162pVMelbVtu7EuNFN079No3OmvFIYWqCYjbUD5wL49jz7AmMhg0UXkEY/LMF1dBmVNv07kfsodcUOVjLmUMasvxz15DLGFTbKpjTxY9JNKciPs7IUD6QrfADKQSMhmKK2aBOq9j1od5/QaIB10a1IRuypN8CbMdIj26he/kgH/JFxrlYW60e9F8SW45EapbYSXjhH0WBQ3xhdr7hAvHw5ag9WDISw5vLAbFDCSYZ2LBl+thcdZQe7jENPEI2yxE9DpWe2r89gFiPhaVnVSJptvRa+vI3zjjVSKau+xP1E/j1wIDAQAB"; // 支付宝公钥
    //回调
    public static final String NOTIFY_URL = "https://dev.chongdaopet.com/app_client/api/pay/aliPayCallback";
     // 支付完成跳转页面
    public static final String RETURN_URL = "";
    // 请求网关地址（现为沙箱环境）
    public static final String GATEWAY = "https://openapi.alipaydev.com/gateway.do";
    // 编码格式
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";
    // RSA2
    public static final String SIGN_TYPE = "RSA2";







}
