package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the dic_info database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="dic_info")
public class DicInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String code;

	private String note;

	private Integer status;

	private String val;

	@Override
	public String toString() {
		return "DicInfo{" +
				"id=" + id +
				", code='" + code + '\'' +
				", note='" + note + '\'' +
				", status=" + status +
				", val='" + val + '\'' +
				'}';
	}
}