package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 礼包券
 * @Date 9:04 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "package_card")
public class Packagecard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer packageId;//礼包id
	private Integer cardId;//优惠券id
	private Integer amount;//数量
	private Integer status;

	private Date createTime;
	private Date updateTime;

}