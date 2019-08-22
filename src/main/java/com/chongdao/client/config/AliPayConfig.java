package com.chongdao.client.config;

public class AliPayConfig {

    public static final String ALI_PAY_APPID = "2019082066320830"; // appid

    public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDLtA4N6A0QP0HvjZPaZhfZIMp9czInjwOrVzfxf4g1y1TCX2ajB5JEnOz5vpTfVQCsJachQ26g6PV1K3d63Vaydb7CgQQr9ycoDsOL3Kz2ooDnVvNMTkTK6+kOdBQmzjwDyE9jedzh3NQKw598Usf0OLim2QEtywpbWh0AInr7UZToPyVBUFm/FFzh2SrNGzYVKtB36KreCFqv87Wwdkiz7KSA2PUmaAnxjwYnjAT4CnNL604c3WYup2qtobmt8PZTRMGbzrQP4y6I8B/rrOQWxjjGWjPeSPoTzgqRqvbla0+z0j+TUilUwY0HEOvYU7BPurLwq5C+bMGYtJY+w1ShAgMBAAECggEBAJBEmjuRIEdkdb9CRvfDnJpDlgWZI2lO6WtdHxrjlsV6lzH+PQOzpg0AEvmh13AAlLRXCnINfvlpZAYy3bDft7o2l/7LPvggV9QP+MBCbyFR3/d35mIhgKkywWFiJq+BGpIXONqcASrZqjdhar+zJTg7P9OzFxmmllQykx14Th9U2PB+SgKXRvnDY0FuyzKJ5+r0L4/0XC3PMVry9Q1zI6XHMKKCh+6zdK9EYMlC55PfGetaebv+jU3zAoo3eU/cYRnPvBu8SxCcE5oYmyvghk44RUGjppHOrL2Y+66DGHI0BMGJta3nou1K7ILVPEJWeESAy4rzv49mJeIxmJCkl0ECgYEA9CGmc4irqCdihRs42AEV+FrTqh/cdYe7WUs/mB3DMUew9+TMHyvH4kEQCoZereekMSFW4k6p6UUvM7QBt3mR58MM2kaAmsqMiYVnnyKfLpiMfJXITbtwpwqXNApOIX7heI50qmbAO3YvDSxUrHypa7EvtUS8moehWyXBnaw6SykCgYEA1ZtBNfR9b+Rm1SEj4rzQVC00bVf59mTN7anukBUu6FDSecOjoa4eWLqZmakWhgYe0spIgBa3f7BjbJ4AjcV6rbMneTm8kaVxzTFmC+j+yHqLJWtucF4V0Cy60kYLVmC9N5YQ3IpuLGM1ddK6cYSbnDGjzgC+OAYPwdlsdhjvZLkCgYEA0hpLuE68/MC4hzmAWFXG5LC2TUmSYAvqbtTV7EJhBH5gjVd5XLI/GjdIaOZvgVdD9Yxty+JLVJjYILUgbRjROxagbxISCCWdgZUwMsKHIIDlFyuI8W0JbJkuu/jKeQVLYoenXCeF9SanI70tOi0nWalqF6UKdCuZ8Gm0EdBxctECgYAy/f4iQDxVosO9q0yiywo8nhH+7FqarFPQoZl2wefRw8GLGd4NMcDC08DpQslCs2FWVr3Z/hJOJDCH9GwTbAouPy90LAlNB57UKi+/Nh7kgwSGqU/S+VLhwFvU6K26MBvbkjJyQ4WriP9xIh4qDUsMjXkaK2k9fIz0bNAMnUVA2QKBgQDG73siL4/iHAv9kXXD4sthuJCIsSVVstwQuIrDRAw7Dz815iXCKtrkEjLJu0jUUzfZI5NuXwd6uWJxVdJ8MMSMTkv7FjtpgIEqinpVoMnNlejFkXlcAJinXXdB8+DHDv93NT6CIDFmsqwPEENCSG6/0SP7hbz8RkN253tJLcWM+w=="; // app支付私钥

    public static final String ALI_PAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy7QODegNED9B742T2mYX2SDKfXMyJ48Dq1c38X+INctUwl9moweSRJzs+b6U31UArCWnIUNuoOj1dSt3et1WsnW+woEEK/cnKA7Di9ys9qKA51bzTE5EyuvpDnQUJs48A8hPY3nc4dzUCsOffFLH9Di4ptkBLcsKW1odACJ6+1GU6D8lQVBZvxRc4dkqzRs2FSrQd+iq3ghar/O1sHZIs+ykgNj1JmgJ8Y8GJ4wE+ApzS+tOHN1mLqdqraG5rfD2U0TBm860D+MuiPAf66zkFsY4xloz3kj6E84Kkar25WtPs9I/k1IpVMGNBxDr2FOwT7qy8KuQvmzBmLSWPsNUoQIDAQAB"; // 支付宝公钥
    //回调
    public static final String NOTIFY_URL = "https://dev.chongdaopet.com/app_client/api/pay/aliPayCallback";
     // 支付完成跳转页面
    public static final String RETURN_URL = "";
    // 请求网关地址（）
    //沙箱网关https://openapi.alipaydev.com/gateway.do
    public static final String GATEWAY = "https://openapi.alipay.com/gateway.do";
    // 编码格式
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";
    // RSA2
    public static final String SIGN_TYPE = "RSA2";







}
