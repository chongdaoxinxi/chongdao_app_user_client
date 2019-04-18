package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 配送员
 * @Date 17:36 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="express")
public class Express extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String description;

	private String name;

	private String password;

	private Integer status;
	
	private Integer type;

	private String tel;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private String username;

	private Integer version;

	private String areaCode;
	private Double lastLng;

	private Double lastLat;
	private String headImg;

	private String openId;

	private String areaName;

	@Override
	public String toString() {
		return "Express{" +
				"id=" + id +
				", createby=" + createby +
				", createdate=" + createdate +
				", description='" + description + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", status=" + status +
				", type=" + type +
				", tel='" + tel + '\'' +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", username='" + username + '\'' +
				", version=" + version +
				", areaCode='" + areaCode + '\'' +
				", lastLng=" + lastLng +
				", lastLat=" + lastLat +
				", headImg='" + headImg + '\'' +
				", openId='" + openId + '\'' +
				", areaName='" + areaName + '\'' +
				'}';
	}
}