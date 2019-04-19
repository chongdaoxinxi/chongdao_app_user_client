package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 库存(暂时废弃)
 * @Date 17:41 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "inventory")
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private Integer shopid;
	private Integer type;
	private Integer status;
	private Integer available;
	private String comment;
	private Integer stock;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

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