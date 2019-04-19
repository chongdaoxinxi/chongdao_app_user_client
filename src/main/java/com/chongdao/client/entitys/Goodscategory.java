package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 商品分类
 * @Date 17:40 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="goodscategory")
public class Goodscategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String comment;
	private Integer board;//不详
	private Integer inventorytype;//不详
	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "Goodscategory [id=" + id + ", board=" + board + ", inventorytype=" + inventorytype + ", comment="
				+ comment + ", createby=" + createby + ", createdate=" + createdate + ", name=" + name + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}