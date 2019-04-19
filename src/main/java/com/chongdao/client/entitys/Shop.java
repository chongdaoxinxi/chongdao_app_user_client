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
 * @Author onlineS
 * @Description 商家表
 * @Date 9:07 2019/4/19
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shop")
public class Shop extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String accountname;//登录名
	private String password;
	@Column(name="user_id")
	private Integer userId;
	private String logo;
	private String tel;
	private String address;
	private BigDecimal money;
	private Integer type;
	private Integer status;
	private Double grade;//级别
	@Column(name="module_ids")
	private String moduleIds;//支持模块,存储muduleid, 以,链接
	@Column(name="area_id")
	private Integer areaId;
	@Column(name="area_code")
	private String areaCode;
	private Double lat;
	private Double lng;

	private String des;//店铺描述
	@Column(name="show_img")
	private String showImg;//店铺展示图片

	private Integer starttime;//开始营业时间
	private Integer endtime;//结束营业时间
	@Column(name="goods_price")
	private BigDecimal goodsPrice;
	@Column(name="service_price")
	private BigDecimal servicePrice;

	@Column(name="is_delivery")
	private Integer isDelivery;
	@Column(name="is_safety")
	private Integer isSafety;

	@Column(name="wx_no")
	private String wxNo;
	@Column(name="zfb_no")
	private String zfbNo;
	@Column(name="bankno")
	private String bankNo;

	private Integer ishot;//是否热门商家
	private Integer autoaccept;//是否自动接单
	private Integer isStop;//是否停止营业
	@Column(name="stop_note")
	private String stopNote;//停止营业公告

	/**  商家二维码 */
	private String qrCodeUrl;
	/** 参与公益：0，未参与，1参与 */
	private Integer joinCommonWeal;
	/** 参与奖励活动：0，未参与 1.参与 */
	private Integer activeStatus;

	//废弃字段
	@Transient
	private Integer isRecom;
	@Transient
	private Integer showType;

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