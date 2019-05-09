package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 系统统计(每日的用户进入次数, 注册次数)
 * @Date 9:20 2019/4/19
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="statistic")
public class Statistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private double entry;//进入小程序次数
	private double register;//注册小程序次数
	private Date currentTime;//当前日期
	private Date createTime;
	private Date updateTime;
}