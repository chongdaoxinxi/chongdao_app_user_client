package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the area database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="area")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String code;

	@Column(name="is_open")
	private Integer isOpen;

	private Integer level;

	private String name;

	private Integer pid;

	private Integer rate;
	
	private Double lat;

	private Double lng;

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