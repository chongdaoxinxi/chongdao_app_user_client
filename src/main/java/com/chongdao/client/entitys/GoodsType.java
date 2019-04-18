package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the goods_type database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "goods_type")
public class GoodsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer board;

	@Column(name="module_id")
	private Integer moduleId;

	private String name;

	@Column(name="shop_id")
	private Integer shopId;

	private String sort;

	private Integer status;

	@Override
	public String toString() {
		return "GoodsType{" +
				"id=" + id +
				", board=" + board +
				", moduleId=" + moduleId +
				", name='" + name + '\'' +
				", shopId=" + shopId +
				", sort='" + sort + '\'' +
				", status=" + status +
				'}';
	}
}