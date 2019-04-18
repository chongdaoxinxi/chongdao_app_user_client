package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 收藏的商品
 * @Date 17:37 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="favouritegood")
public class Favouritegood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer goodsid;

	private Integer status;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer userid;

	private Integer version;

	@Override
	public String toString() {
		return "Favouritegood{" +
				"id=" + id +
				", createby=" + createby +
				", createdate=" + createdate +
				", goodsid=" + goodsid +
				", status=" + status +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", userid=" + userid +
				", version=" + version +
				'}';
	}
}