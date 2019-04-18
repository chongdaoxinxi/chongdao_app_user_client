package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the module database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "module")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String icon;

	private String name;

	@Column(name="service_money")
	private BigDecimal serviceMoney;

	private Integer sort;

	private Integer status;

	private Integer type;

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