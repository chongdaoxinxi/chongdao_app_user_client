package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 通知(暂时废弃)
 * @Date 17:43 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "notification")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String content;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private String orderno;
	private String phone;
	private Integer status;
	private String title;
	private Integer receiverid;
	private Integer receivertype;
	private Integer type;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer version;

	@Override
	public String toString() {
		return "Notification [id=" + id + ", content=" + content + ", createby=" + createby + ", createdate="
				+ createdate + ", orderno=" + orderno + ", phone=" + phone + ", status=" + status + ", title=" + title
				+ ", receiverid=" + receiverid + ", receivertype=" + receivertype + ", type=" + type + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}