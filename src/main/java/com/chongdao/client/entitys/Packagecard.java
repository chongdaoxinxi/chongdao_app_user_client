package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the packagecard database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "packagecard")
public class Packagecard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer amount;

	private Integer cardid;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer packageid;

	private Integer status;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Packagecard{" +
				"id=" + id +
				", amount=" + amount +
				", cardid=" + cardid +
				", createby=" + createby +
				", createdate=" + createdate +
				", packageid=" + packageid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}