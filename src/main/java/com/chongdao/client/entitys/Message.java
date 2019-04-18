package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String content;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer receiverid;

	private Integer receivertype;

	private Integer status;

	private String title;
	
	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", createby=" + createby + ", createdate=" + createdate
				+ ", receiverid=" + receiverid + ", receivertype=" + receivertype + ", status=" + status + ", title="
				+ title + ", type=" + type + ", updateby=" + updateby + ", updatedate=" + updatedate + ", version="
				+ version + "]";
	}
}