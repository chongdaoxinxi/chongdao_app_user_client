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
 * @Description 模块
 * @Date 17:43 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "module")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String icon;
	private Integer sort;
	private Integer status;//0||1
	@Column(name="service_money")
	private BigDecimal serviceMoney;
	private Integer type;
	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "Module{" +
				"id=" + id +
				", icon='" + icon + '\'' +
				", name='" + name + '\'' +
				", serviceMoney=" + serviceMoney +
				", sort=" + sort +
				", status=" + status +
				", type=" + type +
				'}';
	}
}