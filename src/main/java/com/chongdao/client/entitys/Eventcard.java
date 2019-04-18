package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the eventcard database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="eventcard")
public class Eventcard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer cardid;

	private Integer count;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer eventid;

	private Integer status;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Eventcard{" +
				"id=" + id +
				", cardid=" + cardid +
				", count=" + count +
				", createby=" + createby +
				", createdate=" + createdate +
				", eventid=" + eventid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}