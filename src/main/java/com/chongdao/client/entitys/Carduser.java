package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the carduser database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="carduser")
public class Carduser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer cardid;
	
	private Integer eventid;

	private Integer count;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer status;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer userid;

	private Integer version;

	private Integer shopId;

	private Integer couponId;

	@Override
	public String toString() {
		return "Carduser [id=" + id + ", cardid=" + cardid + ", eventid=" + eventid + ", count=" + count + ", createby="
				+ createby + ", createdate=" + createdate + ", status=" + status + ", updateby=" + updateby
				+ ", updatedate=" + updatedate + ", userid=" + userid + ", version=" + version + "]";
	}
	
}