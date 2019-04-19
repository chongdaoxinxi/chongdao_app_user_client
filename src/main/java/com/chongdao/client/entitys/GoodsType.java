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
	@Column(name="shop_id")
	private Integer shopId;
	@Column(name="module_id")
	private Integer moduleId;//所属模块id
	private Integer board;//所属分类id
	private String sort;
	private Integer status;
	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "GoodsType{" +
				"id=" + id +
				", name='" + name + '\'' +
				", shopId=" + shopId +
				", moduleId=" + moduleId +
				", board=" + board +
				", sort='" + sort + '\'' +
				", status=" + status +
				", version=" + version +
				", createby=" + createby +
				", createdate=" + createdate +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				'}';
	}
}