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

	private String ShopNewOrder;


	private String ExpressNewOrder;

	private String orderasigned;

	private String ShopPetArrived;

	private String UserRefund;

	private String UserRefundArrived;

	private String UserTopUp;

	private String UserPetArrived;

	private String ExpressServiceComplete;

	private String ShopAcceptOrder;

	private String ShopRefuseOrder;

	private String ShopNewAdtOrder;

	private String UserNewAdtOrder;

	private String UserAcceptAdtOrder;

	private String UserRefuseAdtOrder;

	private String UserRefundAdtOrder;

	private String UserOrderTimeOutNotAccept;

	private String ExpressRefundOrder;

	private String ShopAcceptRefund;

	private String UserOrderRefund;

	private String ShopTimeoutNotAccept;

	private String NewSendMessage;
	private String MerchantOrder;
	private String PetsServed;
	private String MerchantCompletion;
	private String PickUpPets;
	private String ShopNewOrderAuto;

	private String ShopUserRefund;

	private  String PickUpQuickly;
	private  String FavouriteServed;

	private String GoodsServed;

}
