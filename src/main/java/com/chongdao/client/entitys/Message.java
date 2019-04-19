package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 信息(暂时废弃)
 * @Date 17:42 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String content;
	private Integer receiverid;
	private Integer receivertype;
	private Integer status;
	private String title;
	private Integer type;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", createby=" + createby + ", createdate=" + createdate
				+ ", receiverid=" + receiverid + ", receivertype=" + receivertype + ", status=" + status + ", title="
				+ title + ", type=" + type + ", updateby=" + updateby + ", updatedate=" + updatedate + ", version="
				+ version + "]";
	}
}