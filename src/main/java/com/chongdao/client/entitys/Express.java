package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 配送员
 * @Date 17:36 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="express")
public class Express extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String username;//用户名
	private String password;
	private String nativePassword;//未加密过的密码, 便于PC端编辑
	private String name;//姓名
	private String phone;
	private Integer type;//1||2
	private Integer status;//-1||1
	private String description;
	private String areaCode;//区域码
	private String areaName;//区域名称
	private Double lastLng;
	private Double lastLat;
	private Date createTime;
//
//	//暂时废弃
//	private String headImg;
//	private String openId;
}