package com.chongdao.client.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the shop database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String accountname;

	private String address;

	private String areaCode;

	private Integer areaId;

	private String des;

	private Integer endtime;

	private BigDecimal goodsPrice;

	private Double grade;

	private Integer isDelivery;

	private Integer isSafety;

	private Double lat;

	private Double lng;

	private String logo;

	private String moduleIds;

	private BigDecimal money;

	private String name;

	private String password;

	private BigDecimal servicePrice;

	private String showImg;



	private Integer starttime;

	private Integer status;

	private String stopNote;

	private String tel;

	private Integer type;

	private Integer userId;

	private String wxNo;

	private String zfbNo;
	
	private String bankNo;
	
	private Integer ishot;
	
	private Integer autoaccept;

	private Integer isStop;

	/**  商家二维码 */
	private String qrCodeUrl;

	/** 参与公益：0，未参与，1参与 */
	private Integer joinCommonWeal;

	/** 参与奖励活动：0，未参与 1.参与 */
	private Integer activeStatus;


}