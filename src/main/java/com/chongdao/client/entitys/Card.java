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
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String comment;//描述
	private BigDecimal fullPrice;//触发价格(例如满100减10, 触发价格为100)
	private BigDecimal decreasePrice;//优惠金额
	private Integer type;//1:通用(官方);2:抵用券、免费洗;3.单程;4.双程5.公益
	private Integer status = 0;//-1删除，0上架，1下架（默认为0）

	private Date startTime;
	private Date endTime;
	private Date createTime;
	private Date updateTime;

	private String shopIds;//支持商店(以,连接的字符串)
	private String areaIds;//支持区域id(以,连接的字符串)
	private String areaCode;

	@Transient
	private Integer couponId;
}