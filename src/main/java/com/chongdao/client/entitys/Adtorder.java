package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author onlineS
 * @Description 追加订单
 * @Date 17:18 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="adtorder")
public class Adtorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer orderid;//订单id, order_info.id
	private String adtorderno;//追加订单号
	@Column(name="shop_id")
	private Integer shopId;//商店id
	@Column(name="user_id")
	private Integer userId;//用户id
	private String payuser;
	private Double discount;
	private BigDecimal discountmoney;
	private BigDecimal goodsprice;
	private BigDecimal totalprice;
	private String note;
	private Integer status;

	private Integer version;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer updateby;

	@Override
	public String toString() {
		return "Adtorder [id=" + id + ", adtorderno=" + adtorderno + ", createby=" + createby + ", createdate="
				+ createdate + ", discount=" + discount + ", discountmoney=" + discountmoney + ", goodsprice="
				+ goodsprice + ", note=" + note + ", orderid=" + orderid + ", payuser=" + payuser + ", shopId=" + shopId
				+ ", status=" + status + ", totalprice=" + totalprice + ", updateby=" + updateby + ", updatedate="
				+ updatedate + ", userId=" + userId + ", version=" + version + "]";
	}
}