package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @Author onlineS
 * @Description 区域
 * @Date 17:19 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="area")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String code;
	private Integer pid;//上级区域id(例如:上海市为嘉定区的上级)
	private Double lat;
	private Double lng;
	private Integer level;//层级关系
	@Column(name="is_open")
	private Integer isOpen;//该地区是否开放
	private Integer rate;

	@Override
	public String toString() {
		return "Area{" +
				"id=" + id +
				", code='" + code + '\'' +
				", isOpen=" + isOpen +
				", level=" + level +
				", name='" + name + '\'' +
				", pid=" + pid +
				", rate=" + rate +
				", lat=" + lat +
				", lng=" + lng +
				'}';
	}
}