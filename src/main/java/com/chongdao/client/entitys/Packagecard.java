package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 礼包券
 * @Date 9:04 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "packagecard")
public class Packagecard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer packageid;//礼包id
	private Integer cardid;//优惠券id
	private Integer amount;//数量
	private Integer status;

	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer version;

	@Override
	public String toString() {
		return "Packagecard{" +
				"id=" + id +
				", amount=" + amount +
				", cardid=" + cardid +
				", createby=" + createby +
				", createdate=" + createdate +
				", packageid=" + packageid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}