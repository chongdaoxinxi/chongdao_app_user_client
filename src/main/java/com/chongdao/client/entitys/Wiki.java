package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 维基(暂时废弃)
 * @Date 9:38 2019/4/19
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wiki")
public class Wiki implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Lob
	private String content;
	@Lob
	private String Integerro;
	private String picture;
	private Integer pid;
	@Lob
	private String title;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "Wiki{" +
				"id=" + id +
				", content='" + content + '\'' +
				", createby=" + createby +
				", createdate=" + createdate +
				", Integerro='" + Integerro + '\'' +
				", picture='" + picture + '\'' +
				", pid=" + pid +
				", title='" + title + '\'' +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", version=" + version +
				'}';
	}
}