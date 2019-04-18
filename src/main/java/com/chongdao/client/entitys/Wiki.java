package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the wiki database table.
 * 
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

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Lob
	private String Integerro;

	private String picture;

	private Integer pid;

	@Lob
	private String title;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

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