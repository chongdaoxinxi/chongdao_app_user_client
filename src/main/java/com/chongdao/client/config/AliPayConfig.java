package com.chongdao.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AliPayConfig {

    public static final String ALI_PAY_APPID = "2016093000631040"; // appid

    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOqoYggFbJTMoMEu+WPXSzTVCpIq393NbJ4OraR0u3G8VggogN+CwPQs46itZNsN2JPJRv2kjFXn7J3BwWKCdUoX2o/xeBOqLCDSjYm0rmvHgj1eA6ViAILN0y/RVLdMfeU9r0aH+RqNHNNJoAlFoF+jbj5konJHG4FU6Sbi1iGmtmW0Ok9UgDePQ1UYrsR4SwHK5NIn5tuF/nzldMdDt8WrS2oLugBO7cFQ45xNGLcdC7HNtTo+bECCRXYnOgFnVza/F8B9IfqmWqNfIXyMYT2/aoUH7IWxb7NSn0Lz019ixRlUcZ0B1/h8DEcjF+nne+4XnMzrfJAHmR1JsIY895AgMBAAECggEASKACG3V5uIvApxjUojSyvbvz/cXLsNnE9YvrwiZdIt+eQJzySbo2I6aseRzIL1CD1iLipXYHaYHLJVNJcPYL34c+AtOo0X5Z8FLmoi2bmY4D0qpDtS62q7K7AAPZ3UmbnVAuvBCeMDYyZzYJ15WtpjFT0/wdb4Ob6EvgbaZsr+1kafByuO/IjXjsMW0SUQla17tWvUTmXAk3qlyNJXPGWLPv9pKYWehrR3eEZD9Qfv/UogjGBzh7FIlN6ILl0UqZ3vjLGvW7EwYp0H8gv8MdjSp67P+uLQtOFsgs6XPQk0x3GuCYBAigu3XAWsAVzaFXRIRhhVg31Cl5TAO63827AQKBgQDE7AVTF+uOWcoFwH7hCzs+hIRWNG7h/kjGTU31HmALpwgftFHBxbTSe6FtSHvXZHu8bRqx6mqt7ucvWcUjwDZj3CH+kGiSLh3AnJAIIs4sWEgwzNdSO6Kbrgom8oMh+cKi1xRmaz3M1M+6HJ8NImrrKkbZ58ZtE5GThcAwvTedSQKBgQC5d40jE8tcGV20Ew3Xw+Q8N5w5JxWWgRaHiyctJDQrzwsnJxcq/UhKS5bZxgZR/rBQyZnNk2hxGfi22ldjs302Bw31KbV00qWTrPirbWAn05wupjccxIKe+2nf5VoT2EUytnc3uWLXcRpuXYr6JK7mXEDUhVzIRfmHyoKk+7+QsQKBgGr09v9m4p5yznPJViDNhOQz/d0EpaVCBC0RF+KnGOu7UYJG4pEo3UnltIwy7nDTNxHlALbZ6A+IzzC8xYLhh6k9i4lEzkd3nNldMC37GZikarF20QztLyj3orP8zJTLn5fq0j2+ydZTUHB50znDE+efEtSQOwpLsqo532WZoHdhAoGBAJMgcJjz+e28vaW9O8z/PA48+zQneiujidDKpbBcuPpLy4gqrWlP9cniPu/Q9HCVPx9HXjhDiKW4OaIsgQi5eUmjvQHQLX9YHzuQCtBK+u19I7ep6axrg0dFIE8Z7SQy+2nob5iH+w33TkVg9X/MG+boJhGxHdJOGE4NayggAW5xAoGAHK/TD4Ua1tbKRYuHAMzFmGZxEmDXf1IF2de9Kwhpa2ixUJn5tuCb1LB8fZDLD4jWmOXgPyx9BB4sinFLPEEWulu44aD0bq9mnYOIPlXa56X6cJwybpyayZgpge6Zzf5pBKTbDvUfhDfmxY60agTNhswUvbSmG5b7qkccmhBBsdI="; // app支付私钥

    public static final String ALI_PAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjqqGIIBWyUzKDBLvlj10s01QqSKt/dzWyeDq2kdLtxvFYIKIDfgsD0LOOorWTbDdiTyUb9pIxV5+ydwcFignVKF9qP8XgTqiwg0o2JtK5rx4I9XgOlYgCCzdMv0VS3TH3lPa9Gh/kajRzTSaAJRaBfo24+ZKJyRxuBVOkm4tYhprZltDpPVIA3j0NVGK7EeEsByuTSJ+bbhf585XTHQ7fFq0tqC7oATu3BUOOcTRi3HQuxzbU6PmxAgkV2JzoBZ1c2vxfAfSH6plqjXyF8jGE9v2qFB+yFsW+zUp9C89NfYsUZVHGdAdf4fAxHIxfp53vuF5zM63yQB5kdSbCGPPeQIDAQAB"; // 支付宝公钥

    public static final String NOTIFY_URL = "https://dev.chongdaopet.com/api/pay/ali_pay_callback";
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
