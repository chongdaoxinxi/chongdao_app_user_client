package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 订单投诉
 * @Date 9:30 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="usercomplain")
public class UserComplain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	@Column(name="user_id")
	private int userId;
	@Column(name="order_id")
	private int orderId;
	private String note;

	private int version;
	private int updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private int createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Override
	public String toString() {
		return "Usercomplain [id=" + id + ", createby=" + createby + ", createdate=" + createdate + ", note=" + note
				+ ", orderId=" + orderId + ", updateby=" + updateby + ", updatedate=" + updatedate + ", userId="
				+ userId + ", version=" + version + "]";
	}
}