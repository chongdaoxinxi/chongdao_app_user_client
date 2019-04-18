package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the adtorder database table.
 *
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="adtorder")
public class Adtorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String adtorderno;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Double discount;

	private BigDecimal discountmoney;

	private BigDecimal goodsprice;

	private String note;

	private Integer orderid;

	private String payuser;

	@Column(name="shop_id")
	private Integer shopId;

	private Integer status;

	private BigDecimal totalprice;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Column(name="user_id")
	private Integer userId;

	private Integer version;



	@Override
	public String toString() {
		return "Adtorder [id=" + id + ", adtorderno=" + adtorderno + ", createby=" + createby + ", createdate="
				+ createdate + ", discount=" + discount + ", discountmoney=" + discountmoney + ", goodsprice="
				+ goodsprice + ", note=" + note + ", orderid=" + orderid + ", payuser=" + payuser + ", shopId=" + shopId
				+ ", status=" + status + ", totalprice=" + totalprice + ", updateby=" + updateby + ", updatedate="
				+ updatedate + ", userId=" + userId + ", version=" + version + "]";
	}

}