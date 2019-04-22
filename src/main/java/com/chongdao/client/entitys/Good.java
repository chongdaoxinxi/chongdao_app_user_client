package com.chongdao.client.entitys;


import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/** 
 * @Author onlineS
 * @Description 商品
 * @Date 17:39 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "goods")
public class Good extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String icon;//图片
	@Lob
	private String des;//商品介绍
	private Double discount = 0.0D;
	private BigDecimal price;
	private String unit;//计量单位
	@Column(name="shop_id")
	private Integer shopId;//商店id
	@Column(name="module_id")
	private Integer moduleId;//所属模块id
	@Transient
	private Integer goodsType;//所属分类id
	@Column(name="type_id")
	private Integer typeId;//所属类别id
	private Integer status;//-1: 删除, 0:下架, 1:上架
	@Transient
	private String areaCode;//区域码
    //提高系数默认为1
    private Double ratio = 1.0D;
    private BigDecimal ratioPrice;

	@Override
	public String toString() {
		return "Good{" +
				"id=" + id +
				", des='" + des + '\'' +
				", discount=" + discount +
				", icon='" + icon + '\'' +
				", moduleId=" + moduleId +
				", name='" + name + '\'' +
				", price=" + price +
				", shopId=" + shopId +
				", status=" + status +
				", typeId=" + typeId +
				", unit='" + unit + '\'' +
				", goodsType=" + goodsType +
				", areaCode='" + areaCode + '\'' +
				", ratio=" + ratio +
				", ratioPrice=" + ratioPrice +
				'}';
	}
}