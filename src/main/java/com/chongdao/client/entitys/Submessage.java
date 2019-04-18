package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the submessage database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="submessage")
public class Submessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String content;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer messageid;

	private Integer receiverid;

	private Integer status;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Submessage [id=" + id + ", content=" + content + ", createby=" + createby + ", createdate=" + createdate
				+ ", messageid=" + messageid + ", receiverid=" + receiverid + ", status=" + status + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}