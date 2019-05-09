package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 模块
 * @Date 17:43 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "module")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String icon;
	private Integer type;//1:商品;2:服务
	private Integer sort;
	private Integer status;//-1非正常||0暂停||1正常
	private Date createTime;

}