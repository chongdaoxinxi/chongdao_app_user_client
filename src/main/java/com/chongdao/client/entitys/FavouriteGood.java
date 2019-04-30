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
@Table(name="favourite_good")
public class FavouriteGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer goodsId;//商品id
	private Integer userId;//用户id
	private Integer status;//-1:取消收藏, 1:收藏
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@Override
	public String toString() {
		return "FavouriteGood{" +
				"id=" + id +
				", goodsId=" + goodsId +
				", userId=" + userId +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}