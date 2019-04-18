package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the usercomplain database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="usercomplain")
public class UserComplain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;

	private int createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String note;

	@Column(name="order_id")
	private int orderId;

	private int updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Column(name="user_id")
	private int userId;
	
	private int version;

	@Override
	public String toString() {
		return "Usercomplain [id=" + id + ", createby=" + createby + ", createdate=" + createdate + ", note=" + note
				+ ", orderId=" + orderId + ", updateby=" + updateby + ", updatedate=" + updatedate + ", userId="
				+ userId + ", version=" + version + "]";
	}
}