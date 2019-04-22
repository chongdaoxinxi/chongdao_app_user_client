package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/** 
 * @Author onlineS
 * @Description
 * @Date 9:27 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "recomm")
public class Recomm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="conn_id")
	private Integer connId;
	private Integer sort;
	private Integer type;

	@Override
	public String toString() {
		return "Recomm{" +
				"id=" + id +
				", connId=" + connId +
				", sort=" + sort +
				", type=" + type +
				'}';
	}
}