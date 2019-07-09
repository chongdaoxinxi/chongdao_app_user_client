package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import com.chongdao.client.utils.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author onlineS
 * @Description 商家表
 * @Date 9:07 2019/4/19
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shop extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	private String shopName;

	private String phone;

	private String accountName;

	private String password;

	private Integer areaId;

	private String areaCode;

	private Double lat;

	private Double lng;

	private String logo;

	private BigDecimal money;

	private Integer type;

	private Double grade;

	private String wxNo;

	private String zfbNo;

	private String bankNo;

	private String qrCodeUrl;

	private String des;

	private String showImg;

	private Integer status;

	private Integer servicePriceRatio;

	private String stopNote;

	private Byte isHot;

	private Byte isAutoAccept;

	private Byte isJoinCommonWeal;

	private String startBusinessHours;

	private String endBusinessHours;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date lastLoginTime;

	private String address;

	private Byte isStop;

	@Transient
	private Integer sales;

	@Transient
	private Double discount;

	public Shop(Integer id, String shopName, String phone, String accountName, String password, Integer areaId, String areaCode, Double lat, Double lng, String logo, BigDecimal money, Integer type, Double grade, String wxNo, String zfbNo, String bankNo, String qrCodeUrl, String des, String showImg, Integer status, Integer servicePriceRatio, String stopNote, Byte isHot, Byte isAutoAccept, Byte isJoinCommonWeal, String startBusinessHours, String endBusinessHours, Date createTime, Date updateTime, Date lastLoginTime, String address, Byte isStop) {
		this.id = id;
		this.shopName = shopName;
		this.phone = phone;
		this.accountName = accountName;
		this.password = password;
		this.areaId = areaId;
		this.areaCode = areaCode;
		this.lat = lat;
		this.lng = lng;
		this.logo = logo;
		this.money = money;
		this.type = type;
		this.grade = grade;
		this.wxNo = wxNo;
		this.zfbNo = zfbNo;
		this.bankNo = bankNo;
		this.qrCodeUrl = qrCodeUrl;
		this.des = des;
		this.showImg = showImg;
		this.status = status;
		this.servicePriceRatio = servicePriceRatio;
		this.stopNote = stopNote;
		this.isHot = isHot;
		this.isAutoAccept = isAutoAccept;
		this.isJoinCommonWeal = isJoinCommonWeal;
		this.startBusinessHours = startBusinessHours;
		this.endBusinessHours = endBusinessHours;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.lastLoginTime = lastLoginTime;
		this.address = address;
		this.isStop = isStop;
	}

}