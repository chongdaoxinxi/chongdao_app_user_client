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
@Table(name="favourite_shop")
public class FavouriteShop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer shopId;//商店id
	private Integer userId;//用户id
	private Integer status;//-1:取消收藏, 1:收藏
	private Date createTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "FavouriteShop{" +
				"id=" + id +
				", shopId=" + shopId +
				", userId=" + userId +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}