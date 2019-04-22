package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description (暂时废弃)
 * @Date 9:21 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
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