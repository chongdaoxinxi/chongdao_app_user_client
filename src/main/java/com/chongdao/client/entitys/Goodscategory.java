package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the goodscategory database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="goodscategory")
public class Goodscategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	
	private Integer board;
	
	private Integer inventorytype;

	private String comment;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String name;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Goodscategory [id=" + id + ", board=" + board + ", inventorytype=" + inventorytype + ", comment="
				+ comment + ", createby=" + createby + ", createdate=" + createdate + ", name=" + name + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}