package com.chongdao.client.config;

public class AliPayConfig {

    public static final String ALI_PAY_APPID = "2019072365944369"; // appid

    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCERBsyHuiZVvnU/lUmGABA/8DqSKtmQmbxuOPM8+faFSHwV1XHaSpAttsqm9/v+cb4pUucirtOHaGKXq93OjKyHefH6ckK84Y8EWcDydBO1Pb9orQ4R6osLI56B37vHQsdkW5iZwYJ3uioQpQxoAG1ukQu5bC5oI9ODeWbkQekWGMfAsxuNvJ+GPQVcW4XM7r+DHrYEWl4wugulymclm5niWxo5BNEM3P6oXOV0nMaKqNxqP8C+P2Kg2FU/NMTZlfqF+l8h55pqkmhp2C7lESY6DXce87Fbz6q037Sr8JJEPA+wPOCBI7FDzJGaYCvFG5lNjxSyyZT8NzQUD9vPmBHAgMBAAECggEAGluIH+DQ8V3Hw447Ya/+vPU6eHWq0mIBKcJygvFIDsmPTdnZmjd22BaYblFJuwTHhd1KyBNNaC+3dV+RD+1lFOGowsnv6mLFKUs+gB7DiDIgVkMKLvz5CHyImADOZtAe2vGUUb++EFF/g6hg59K/BHvuAnp7UUKRzDgwvWOVZkZ82+I/j11+jPkZNx5lqE2SkdSi4t+05ts0ZKFR3y72MF4/zTcc05NqwZTkgd/aAEQEsURbDIoNwVacejeywr3XBWlshMv1N4i2IzpsAjnKYW9+NiIv6F4inBIpInjKPvUrptl3om2pJ/lcxWFUCAl+9AyP6/TKvhAgmB2fKYApIQKBgQDSSNlGZwT02nsORA4vF89m2ato4soijfZyBlk6HRd/c0HZJRSmHaXJ8SmekuQRqwhUsX1UHmPet0TXSBKYJwJbUlQ4x/DF0w9GlJ4RnoMn2jkS3rjGPoCVLTJH2JexBoYm71SzplHLe7qT+XwDd7wiCccDtMZm/njUczacUIIpFQKBgQChBTlfa4k4eYTXx1vwYWqLgme4gUNcbdC108N+HHN1g62Uc5Tk+5TU/BE7/AY4CGF8M6ThDgnS8FOET3q3OslBIrGKUhmrHztSeo96ne7qL/IukEwgtHJoDka1g5UxBRULuIuQTmIC69HR/t8I1To1qsrNMGQdwj31iohw4M6C6wKBgA3NtmWGsa6ZAObo1L2GpJitxTC3YmluRDUr8YN9EWPOXw4v/vEAlTdyWzcLkWkPfxaAKCfzbr7Z7Ozs0Duoo+D2wIyRo155AlGxw8aITze4fXKdqCdrRumD8/7/WsXGFdxMbZT1X/0pfA/mmptnqrPZTHUT0spD/g6EGZudNU4ZAoGAJrG/sMuBxT2Gi+gPkvuGBBKJZFBD5MFLB3blIrgpktLa8rK31DMU9/2/EoAk6nAxJQywEZkzCSrRlxkWb1PEK0/wBEJogrPVqKNlOV0cndB5zXDFqpJSb+kGWvZM8uO6w/9MIpiDexo6aNFrvY6oF198rsRzOqeEBi6NTTroxZMCgYEAzBM2uKiXJBbnQ+aDPtV4WDPsIsCZdTplupt6Tge6UoJhIgwlqeXmH/nPC44+tYcbnKryHjQST9PMFjzXNFLVVyR5sbMd7kCKpyOo8zqZOTm/KcUaFhPjMPEaY4Qi3M3z9llcfEqmzmKKmnAhXL/e3/2N5eDuVuPRv/vKCnqszZE="; // app支付私钥

    public static final String ALI_PAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhEQbMh7omVb51P5VJhgAQP/A6kirZkJm8bjjzPPn2hUh8FdVx2kqQLbbKpvf7/nG+KVLnIq7Th2hil6vdzoysh3nx+nJCvOGPBFnA8nQTtT2/aK0OEeqLCyOegd+7x0LHZFuYmcGCd7oqEKUMaABtbpELuWwuaCPTg3lm5EHpFhjHwLMbjbyfhj0FXFuFzO6/gx62BFpeMLoLpcpnJZuZ4lsaOQTRDNz+qFzldJzGiqjcaj/Avj9ioNhVPzTE2ZX6hfpfIeeaapJoadgu5REmOg13HvOxW8+qtN+0q/CSRDwPsDzggSOxQ8yRmmArxRuZTY8UssmU/Dc0FA/bz5gRwIDAQAB"; // 支付宝公钥
    //回调
    public static final String NOTIFY_URL = "https://dev.chongdaopet.com/app_client/api/pay/aliPayCallback";
     // 支付完成跳转页面
    public static final String RETURN_URL = "";
    // 请求网关地址（）
    //沙箱网关https://openapi.alipaydev.com/gateway.do
    public static final String GATEWAY = "https://openapi.alipay.com/gateway.do  ";
    // 编码格式
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";
    // RSA2
    public static final String SIGN_TYPE = "RSA2";







}
