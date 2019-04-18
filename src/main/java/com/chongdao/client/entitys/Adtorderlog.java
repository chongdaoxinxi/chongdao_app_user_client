package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 追加订单日志
 * @Date 17:18 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="adtorderlog")
public class Adtorderlog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private String note;
	private Integer adtorderid;
	private Integer orderstatus;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	private Integer version;

	@Override
	public String toString() {
		return "Adtorderlog{" +
				"id=" + id +
				", createby=" + createby +
				", createdate=" + createdate +
				", note='" + note + '\'' +
				", adtorderid=" + adtorderid +
				", orderstatus=" + orderstatus +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}