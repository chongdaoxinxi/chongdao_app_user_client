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
 * @Description 优惠券
 * @Date 17:20 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="card")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String note;
	private String comment;
	private String shopIds;//支持商店(以,连接的字符串)
	private String areaIds;//支持区域id(以,连接的字符串)
	private Integer type;
	private BigDecimal desPrice;//优惠金额
	private BigDecimal fullPrice;//触发价格(例如满100减10, 触发价格为100)

	private String areaCode;
	private Date startTime;
	private Date endTime;
	private Integer couponId;//配送券id(不能重复使用的??)
	private Integer status = 0;//-1删除，0上架，1下架（默认为0）

	private Date createTime;
	private Date updateTime;


}