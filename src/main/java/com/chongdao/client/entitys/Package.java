package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the package database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "package")
public class Package implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String comment;

	private String content;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String description;

	private BigDecimal displayprice;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;

	private Integer goodsid;

	private String name;

	private String note;

	private BigDecimal price;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;

	private Integer status;

	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Package [id=" + id + ", comment=" + comment + ", content=" + content + ", createby=" + createby
				+ ", createdate=" + createdate + ", description=" + description + ", displayprice=" + displayprice
				+ ", enddate=" + enddate + ", goodsid=" + goodsid + ", name=" + name + ", note=" + note + ", price="
				+ price + ", startdate=" + startdate + ", status=" + status + ", type=" + type + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}