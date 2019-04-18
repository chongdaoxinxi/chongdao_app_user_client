package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the recomm database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
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