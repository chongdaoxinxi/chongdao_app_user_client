package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 用户分享记录
 * @Date 9:32 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="user_share")
public class UserShare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="share_code")
	private String shareCode;//分享码
	@Column(name="max_mun")
	private Integer maxMun;//分享次数
	@Column(name="coupon_id")
	private Integer couponId;//配送券id
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
		return "UserShare [id=" + id + ", couponId=" + couponId + ", maxMun=" + maxMun + ", shareCode=" + shareCode
				+ ", userId=" + userId + ", cardid=" + cardid + ", createby=" + createby + ", createdate=" + createdate
				+ ", updateby=" + updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}