package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 充值记录
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="usertransaction")
public class Usertransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String comment;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private BigDecimal money;
	
	private String payuser;

	private Integer orderid;

	private String orderno;

	private Integer status;

	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer userid;

	private Integer version;

	@Override
	public String toString() {
		return "Usertransaction{" +
				"id=" + id +
				", comment='" + comment + '\'' +
				", createby=" + createby +
				", createdate=" + createdate +
				", money=" + money +
				", payuser='" + payuser + '\'' +
				", orderid=" + orderid +
				", orderno='" + orderno + '\'' +
				", status=" + status +
				", type=" + type +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", userid=" + userid +
				", version=" + version +
				'}';
	}
}