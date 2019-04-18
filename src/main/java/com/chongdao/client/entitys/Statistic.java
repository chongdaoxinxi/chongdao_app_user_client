package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the statistic database table.
 * 
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

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date currentdate;

	private double entry;

	private double register;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Statistic{" +
				"id=" + id +
				", createby=" + createby +
				", createdate=" + createdate +
				", currentdate=" + currentdate +
				", entry=" + entry +
				", register=" + register +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}