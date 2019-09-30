package com.chongdao.client.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer deliverAdd;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private String deliverTime;
	
	private Integer goodsdiscard;
	
	private Integer servicediscard;
	
	private BigDecimal goodsdiscount;
	
	private BigDecimal servicediscount;

	private BigDecimal discountMoney;

	private Integer expressId;

	private BigDecimal goodsPrice;

	private Integer isService;

	private String orderNo;

	private Integer orderStatus;

	private Integer petAmount;
	
	private Integer follow;
	
	private BigDecimal payoffamount;

	private BigDecimal purseamount;


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private String receiveTime;

	private BigDecimal servicePrice;

	private Integer shopId;

	private String shopNote;

	private String shopNoteImg;

	private BigDecimal totalPrice;



	private Integer userId;

	private Integer couponId;


	private String areaCode;



}