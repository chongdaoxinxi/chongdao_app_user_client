package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the shop database table.
 * 
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shop")
public class Shop extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String accountname;

	private String address;

	@Column(name="area_code")
	private String areaCode;

	@Column(name="area_id")
	private Integer areaId;

	private String des;

	private Integer endtime;

	@Column(name="goods_price")
	private BigDecimal goodsPrice;

	private Double grade;

	@Column(name="is_delivery")
	private Integer isDelivery;

	@Column(name="is_safety")
	private Integer isSafety;

	@Transient
	private Integer isRecom;

	private Double lat;

	private Double lng;

	private String logo;

	@Column(name="module_ids")
	private String moduleIds;

	private BigDecimal money;

	private String name;

	private String password;

	@Column(name="service_price")
	private BigDecimal servicePrice;

	@Column(name="show_img")
	private String showImg;

	@Transient
	private Integer showType;

	private Integer starttime;

	private Integer status;

	@Column(name="stop_note")
	private String stopNote;

	private String tel;

	private Integer type;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="wx_no")
	private String wxNo;

	@Column(name="zfb_no")
	private String zfbNo;
	
	@Column(name="bankno")
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

	@Override
	public String toString() {
		return "Shop [id=" + id + ", accountname=" + accountname + ", address=" + address + ", areaCode=" + areaCode
				+ ", areaId=" + areaId + ", des=" + des + ", endtime=" + endtime + ", goodsPrice=" + goodsPrice
				+ ", grade=" + grade + ", isDelivery=" + isDelivery + ", isSafety=" + isSafety + ", isRecom=" + isRecom
				+ ", lat=" + lat + ", lng=" + lng + ", logo=" + logo + ", moduleIds=" + moduleIds + ", money=" + money
				+ ", name=" + name + ", password=" + password + ", servicePrice=" + servicePrice + ", showImg="
				+ showImg + ", showType=" + showType + ", starttime=" + starttime + ", status=" + status + ", stopNote="
				+ stopNote + ", tel=" + tel + ", type=" + type + ", userId=" + userId + ", wxNo=" + wxNo + ", zfbNo="
				+ zfbNo + ", bankNo=" + bankNo + ", ishot=" + ishot + ", autoaccept=" + autoaccept + "]";
	}
}