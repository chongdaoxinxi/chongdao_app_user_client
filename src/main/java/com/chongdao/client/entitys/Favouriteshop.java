package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 收藏的商店
 * @Date 17:38 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="favouriteshop")
public class Favouriteshop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer shopid;//商店id
	private Integer userid;//用户id
	private Integer status;//0||1
	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "Favouriteshop{" +
				"id=" + id +
				", createby=" + createby +
				", createdate=" + createdate +
				", shopid=" + shopid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", userid=" + userid +
				", version=" + version +
				'}';
	}
}