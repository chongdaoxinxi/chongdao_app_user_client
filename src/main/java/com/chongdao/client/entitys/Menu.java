package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String name;

	private Integer pid;

	private Integer sort;

	private Integer status;

	private Integer type;

	private String url;
	
    @Transient
    private List<Menu> towMenu;

	public List<Menu> getTowMenu() {
		return towMenu;
	}

	public void setTowMenu(List<Menu> towMenu) {
		this.towMenu = towMenu;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pid=" + pid +
				", sort=" + sort +
				", status=" + status +
				", type=" + type +
				", url='" + url + '\'' +
				", towMenu=" + towMenu +
				'}';
	}
}