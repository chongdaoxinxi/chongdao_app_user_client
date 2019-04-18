package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the wx_coupon database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="wx_coupon")
public class WxCoupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="coupon_id")
	private Integer couponId;
	private String content;
	@Column(name="max_mun")
	private Integer maxMun;
	private String name;
	private Integer status;
	private String image;
	private Integer cardid;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer version;

	@Override
	public String toString() {
		return "WxCoupon [couponId=" + couponId + ", content=" + content + ", maxMun=" + maxMun + ", name=" + name
				+ ", status=" + status + ", image=" + image + ", cardid=" + cardid + ", createby=" + createby
				+ ", createdate=" + createdate + ", updateby=" + updateby + ", updatedate=" + updatedate + ", version="
				+ version + "]";
	}
}