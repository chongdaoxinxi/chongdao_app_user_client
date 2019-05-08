package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 商品类别
 * @Date 17:41 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "goods_type")
public class GoodsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String name;

	private Integer moduleId;

	private Integer categoryId;

	private Integer shopId;

	private Integer sort;

	private Byte status;

	private Date createTime;

	private Date updateTime;

	public GoodsType(Integer id, String name, Integer moduleId, Integer categoryId, Integer shopId, Integer sort, Byte status, Date createTime, Date updateTime) {
		this.id = id;
		this.name = name;
		this.moduleId = moduleId;
		this.categoryId = categoryId;
		this.shopId = shopId;
		this.sort = sort;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
}