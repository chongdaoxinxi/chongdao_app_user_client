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

	private String UserNewSendMessage;
	private String UserOrderTimeOutNotAccept;
	private String UserMerchantOrder;
	private String UserPetsServed;
	private String UserGoodsServed;
	private String UserMerchantCompletion;
	private String UserPetArrived;
	private String UserPickUpPets;
	private String UserRefund;
	private String UserRefundArrived;
	private String UserOrderRefund;
	private String UserTopUp;

	private String ShopNewOrder;
	private String ShopNewOrderAuto;
	private String ShopUserRefund;
	private String ShopPetArrived;
	private String ShopAcceptOrder;
	private String ShopRefuseOrder;
	private String ShopAcceptRefund;
	private String ShopAgreeRefundOrder;
	private String ShopTimeoutNotAccept;

	private String ExpressNewOrder;
	private String ExpressOrderAssigned;
	private String ExpressRefundOrder;
	private String ExpressServiceComplete;
	private String ExpressOrderComplete;

	private String ShopNewAdtOrder;
	private String UserNewAdtOrder;
	private String UserAcceptAdtOrder;
	private String UserRefuseAdtOrder;
	private String UserRefundAdtOrder;
}
