package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 优惠券用户关联表
 * @Date 17:21 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="carduser")
public class Carduser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer cardid;//优惠券id
	private Integer userid;//用户id
	private Integer count;
	private Integer status;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	//暂时没使用
	private Integer shopId;
	private Integer couponId;
	private Integer eventid;


	@Override
	public String toString() {
		return "Carduser [id=" + id + ", cardid=" + cardid + ", eventid=" + eventid + ", count=" + count + ", createby="
				+ createby + ", createdate=" + createdate + ", status=" + status + ", updateby=" + updateby
				+ ", updatedate=" + updatedate + ", userid=" + userid + ", version=" + version + "]";
	}
}