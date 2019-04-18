package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the inventory database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "inventory")
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer available;

	private String comment;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String name;

	private Integer shopid;

	private Integer status;

	private Integer stock;

	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Inventory{" +
				"id=" + id +
				", available=" + available +
				", comment='" + comment + '\'' +
				", createby=" + createby +
				", createdate=" + createdate +
				", name='" + name + '\'' +
				", shopid=" + shopid +
				", status=" + status +
				", stock=" + stock +
				", type=" + type +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}