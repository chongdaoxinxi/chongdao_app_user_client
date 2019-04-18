package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the user_share database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="user_share")
public class UserShare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="coupon_id")
	private Integer couponId;
	@Column(name="max_mun")
	private Integer maxMun;
	@Column(name="share_code")
	private String shareCode;
	@Column(name="user_id")
	private Integer userId;
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
		return "UserShare [id=" + id + ", couponId=" + couponId + ", maxMun=" + maxMun + ", shareCode=" + shareCode
				+ ", userId=" + userId + ", cardid=" + cardid + ", createby=" + createby + ", createdate=" + createdate
				+ ", updateby=" + updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}