package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the favouriteshop database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="favouriteshop")
public class Favouriteshop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer shopid;

	private Integer status;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer userid;

	private Integer version;

	@Override
	public String toString() {
		return "Favouriteshop{" +
				"id=" + id +
				", createby=" + createby +
				", createdate=" + createdate +
				", shopid=" + shopid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", userid=" + userid +
				", version=" + version +
				'}';
	}
}