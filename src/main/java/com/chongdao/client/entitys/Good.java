package com.chongdao.client.entitys;


import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the goods database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "goods")
public class Good extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Lob
	private String des;

	private Double discount = 0.0D;

	private String icon;

	@Column(name="module_id")
	private Integer moduleId;

	private String name;

	private BigDecimal price;

	@Column(name="shop_id")
	private Integer shopId;

	private Integer status;

	@Column(name="type_id")
	private Integer typeId;

	private String unit;
	
    @Transient
    private Integer goodsType;

	@Transient
	private String areaCode;

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