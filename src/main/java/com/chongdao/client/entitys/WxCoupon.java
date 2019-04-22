package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 微信分享活动
 * @Date 9:38 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="wx_coupon")
public class WxCoupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="coupon_id")
	private Integer couponId;
	private String name;
	private String content;
	@Column(name="max_mun")
	private Integer maxMun;//分享次数
	private String image;
	private Integer status;
	private Integer cardid;//优惠券id

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "WxCoupon [couponId=" + couponId + ", content=" + content + ", maxMun=" + maxMun + ", name=" + name
				+ ", status=" + status + ", image=" + image + ", cardid=" + cardid + ", createby=" + createby
				+ ", createdate=" + createdate + ", updateby=" + updateby + ", updatedate=" + updatedate + ", version="
				+ version + "]";
	}
}