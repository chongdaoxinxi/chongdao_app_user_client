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
 * @Description 用户金额交易记录
 * @Date 9:33 2019/4/19
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
	private Integer userid;
	private String payuser;
	private Integer orderid;
	private String orderno;
	private String comment;//注释
	private BigDecimal money;
	private Integer type;//1:用户充值, 2:用户消费, 3:拒单退还
	private Integer status;

	private Integer version;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

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