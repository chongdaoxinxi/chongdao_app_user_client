package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 轮播图
 * @Date 17:20 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="banner")
public class Banner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String img;//图片
	@Column(name="area_code")
	private String areaCode;//区域码
	@Column(name="action_type")
	private Integer actionType;//??
	private Integer status;
	private Integer sort;
	@Column(name="action_val")
	private String actionVal;//??
	private String note;//??
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Override
	public String toString() {
		return "Banner{" +
				"id=" + id +
				", actionType=" + actionType +
				", actionVal='" + actionVal + '\'' +
				", areaCode='" + areaCode + '\'' +
				", createdate=" + createdate +
				", img='" + img + '\'' +
				", note='" + note + '\'' +
				", sort=" + sort +
				", status=" + status +
				'}';
	}
}