package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description
 * @Date 17:36 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="event")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String comment;
	private String content;
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	private String name;
	private String note;
	private String shopids;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	private Integer status;
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
		return "Event{" +
				"id=" + id +
				", comment='" + comment + '\'' +
				", content='" + content + '\'' +
				", createby=" + createby +
				", createdate=" + createdate +
				", description='" + description + '\'' +
				", enddate=" + enddate +
				", name='" + name + '\'' +
				", note='" + note + '\'' +
				", shopids='" + shopids + '\'' +
				", startdate=" + startdate +
				", status=" + status +
				", type=" + type +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}