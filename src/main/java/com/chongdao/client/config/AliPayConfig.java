package com.chongdao.client.config;

public class AliPayConfig {

    public static final String ALI_PAY_APPID = "2019072365944369"; // appid

    // app支付应用私钥
    public static final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDId6r+sfNuJz/6GRp4sZ9TiIxFMv27dHU1VAia/85PM0Ccwf9GfCGY+MKC34XFkO7GX3OuHZpKYC1DAz21KZCaTDJFQUxE7tFRUbWbPHc6XTJ+Ng55vpSCug+tYHvZjkSVRGpGS8LbdmJxd3is98cgnZfNWVDTmQqwYEhJu3tfDNY87RAoOo7M3e4k9vJCRsmcokO9P7YKYA7oP90G+QdvhY3SxL7KRMmT8n4kUNneffbZNEQwyfjgAla0yI/7kdAzubM/yS1WkCJJQstfadCVZ8ZPUg4yK8wZ89t00ApJhJyLmAhozS5wQo4WnBCOwzT43dWT5iV8+8ieFCVrN/OfAgMBAAECggEAfIs8RPgxkKH58VtfQtS3c6JF40gGs0tq9jK7FfEPL81buwgbnKLKDMufrMdJyLB+Z+gtamMh1EtkU1nUIJUuLW5hXP1wcPMeQDNPbJ5oRG5aTGcxHxn0EjOeQczLpSHqIgJbNEklSkuGZZAf/PYDVJQUF5QBzzQpm+J1hrlfsHCjuK6EvRU7or8HGqr55oIAjKDyai/MG5J8vqBwOpAjfkRJjHpOAXKt81Lz8T/QJ1yu4Hsm10+wjtH5W65UTsuAzmMxGE3rWQUxtsuB8g5pWn+VOEHwpejhhBW58kG9HFojucDgluyMpHaJ1/z90D6B08OK6duWVl6t+0Pm2GRgIQKBgQDk96C9amwHJHeGPIC1ymGV13yHpPKZ5VImvH6ta1h6il5lp7Ujk5Oq8ot3UQjGjrIAFyxctUC30uJy+vnJSXgtIhm+4G5//EHR3g8zEU5/S5Gk4ALSeuEpU/6CHfIwJYDZRU4j/YI1VkiiOuhfljPLK+xGeuiL3wEOilz/POXtvQKBgQDgIqca6U21QSsduwc4qVC5P7FsP+xk+4pyYyb5d3WbUOVqm/WcZbFMqs2ES+bP8MaEeEdi+lNb3s7OL+nJgWLZwgAheCIKvZ5/7UTK8m7xw4FgoyYIPDlPrBWAuVrzh2LncNAtdJdUo/QMAkoAHBHaNLpwWxGRWwyhnoFkBcI2iwKBgAn3sbqtOFizQLTKS3wefFMOl3A8QQdsEUB4cjdQdgCXp3Zbb6pF0Wbeui2OjIhATAVu/NJoXZHjD1KcFGWr2hx04JQLpyPRMnhVZ2n8teMawhNgYR8SlFy9OFYnwZRQ/aP3vWgZsCJsSCsvz0/h9Q1CMz36OcdlBOHCKjw+RXAtAoGAO1fxdqae9Ia0q8KBPvdVcDb0+vxR0srm9TJbd2GxudXJaFiyxx4Fu47dq1GNYzEU3q4Z+JLez0xG5UC6XafsTEuZTUNJgB6EgMqJlFJsHYbuEnAf+b44Tmf5qYNqGWCXR0APXnmoN7jRPPy+KxlDgMrFMkpqdFnvFjupdpovudECgYAkhEfahDmHL626eEel7eE4/4yA9Wc47SWEJdS/IPsAWekHxMDZgw3QZF3zb9R7smhKJSjuMBGwYqfXTtuPIOzwL7d9Bzk0qzjqYAoyArOdOjtCeuSTYlIhfx2G3vPv+WSiSHrWL7POPfJRjtQTGpBn6S1X2twWZCM+D2c4xN1yRA==";

    // app支付应用公钥
    public static final String ALI_PAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyHeq/rHzbic/+hkaeLGfU4iMRTL9u3R1NVQImv/OTzNAnMH/RnwhmPjCgt+FxZDuxl9zrh2aSmAtQwM9tSmQmkwyRUFMRO7RUVG1mzx3Ol0yfjYOeb6UgroPrWB72Y5ElURqRkvC23ZicXd4rPfHIJ2XzVlQ05kKsGBISbt7XwzWPO0QKDqOzN3uJPbyQkbJnKJDvT+2CmAO6D/dBvkHb4WN0sS+ykTJk/J+JFDZ3n322TREMMn44AJWtMiP+5HQM7mzP8ktVpAiSULLX2nQlWfGT1IOMivMGfPbdNAKSYSci5gIaM0ucEKOFpwQjsM0+N3Vk+YlfPvInhQlazfznwIDAQAB"; // 支付宝公钥
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
