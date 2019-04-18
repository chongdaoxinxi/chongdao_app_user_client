package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the management database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "management")
public class Management implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String areacode;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Column(name="`e-mail`")
	private String e_mail;

	private String icon;

	private Integer level;

	private String name;

	private String password;

	private Integer status;

	private BigInteger tel;

	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer uplevel;

	private Integer version;

	@Override
	public String toString() {
		return "Management{" +
				"id=" + id +
				", areacode='" + areacode + '\'' +
				", createby=" + createby +
				", createdate=" + createdate +
				", e_mail='" + e_mail + '\'' +
				", icon='" + icon + '\'' +
				", level=" + level +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", status=" + status +
				", tel=" + tel +
				", type=" + type +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", uplevel=" + uplevel +
				", version=" + version +
				'}';
	}
}