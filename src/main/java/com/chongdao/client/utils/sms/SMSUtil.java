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

	private String NewOrderUser;
	private String NewOrderShop;
	private String OrderAutoAcceptShop;
	private String OrderAcceptUser;
	private String OrderAcceptShop;
	private String NewOrderExpress;
	private String OrderTimeOutNotAcceptUser;
	private String OrderTimeoutNotAcceptShop;
	private String OrderRefuseUser;
	private String OrderAssignedExpress;
	private String OrderAssignedUser;
	private String OrderGoodsServedUser;
	private String OrderPetArrivedUser;
	private String OrderPetsServedUser;
	private String OrderPetArrivedShop;
	private String OrderShopServiceCompleteUser;
	private String OrderShopServiceCompleteExpress;
	private String OrderPickUpPetsUser;
	private String OrderUserRefundUser;
	private String OrderUserRefundShop;
	private String OrderUserRefundExpress;
	private String OrderRefundAgreeUser;
	private String OrderRefundAgreeAdmin;
	private String OrderRefundCompleteUser;
	private String OrderExpressCancelUser;
	private String OrderExpressCancelCustom;
	private String UserTopUpUser;
	private String UserTopUpAdmin;
	private String SingleTripGoodServiceStartUser;
	private String SingleTripPetServiceStartUser;

	private String ShopNewAdtOrder;
	private String UserNewAdtOrder;
	private String UserAcceptAdtOrder;
	private String UserRefuseAdtOrder;
	private String UserRefundAdtOrder;
}
