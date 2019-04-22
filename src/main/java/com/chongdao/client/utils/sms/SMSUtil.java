package com.chongdao.client.utils.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * yml配置文件类（短信服务）
 */
@Component
@ConfigurationProperties(prefix = "sms")
@Setter
@Getter
public class SMSUtil {

	private String url;

	private String account;

	private String password;

	private Integer enable;

	private String SmsIdentifyCode;





}
