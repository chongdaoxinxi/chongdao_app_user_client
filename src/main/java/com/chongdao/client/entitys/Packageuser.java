package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 礼包用户关联表
 * @Date 9:04 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "packageuser")
public class Packageuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer userid;
	private Integer packageid;
	private Integer count;
	private Integer status;

	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer version;

	//废弃字段
	private Integer eventid;

	@Override
	public String toString() {
		return "Packageuser{" +
				"id=" + id +
				", count=" + count +
				", createby=" + createby +
				", createdate=" + createdate +
				", eventid=" + eventid +
				", packageid=" + packageid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", userid=" + userid +
				", version=" + version +
				'}';
	}
}