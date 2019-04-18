package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/** 
 * @Author onlineS
 * @Description 优惠规则等
 * @Date 17:35 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
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