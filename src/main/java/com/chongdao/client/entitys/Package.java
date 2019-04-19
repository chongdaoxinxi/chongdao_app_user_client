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
 * @Description 礼包
 * @Date 9:03 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "package")
public class Package implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String note;
	private String comment;
	private String content;
	private Integer goodsid;//商品id
	private String description;
	private BigDecimal displayprice;//例如360元得510元礼包, 这个值就是510
	private BigDecimal price;//价格
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	private Integer status;
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
		return "Package [id=" + id + ", comment=" + comment + ", content=" + content + ", createby=" + createby
				+ ", createdate=" + createdate + ", description=" + description + ", displayprice=" + displayprice
				+ ", enddate=" + enddate + ", goodsid=" + goodsid + ", name=" + name + ", note=" + note + ", price="
				+ price + ", startdate=" + startdate + ", status=" + status + ", type=" + type + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}