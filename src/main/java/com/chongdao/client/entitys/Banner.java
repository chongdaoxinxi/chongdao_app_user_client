package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the banner database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="banner")
public class Banner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Column(name="action_type")
	private Integer actionType;

	@Column(name="action_val")
	private String actionVal;

	@Column(name="area_code")
	private String areaCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String img;

	private String note;

	private Integer sort;

	private Integer status;



}