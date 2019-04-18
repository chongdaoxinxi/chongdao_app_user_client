package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    private String name;
    
    private String pwd;
    
    private Integer status;
    
    private Integer type;
    
    private String icon;
    
    private String openId;
    
    private String areaCode;
    
    private String tel;
    
	private BigDecimal money;
	
    private Integer points;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;
	
	private Integer version;

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pwd=" + pwd + ", status=" + status + ", type=" + type
				+ ", icon=" + icon + ", openId=" + openId + ", areaCode=" + areaCode + ", tel=" + tel + ", money="
				+ money + ", points=" + points + ", createby=" + createby + ", createdate=" + createdate + ", updateby="
				+ updateby + ", updatedate=" + updatedate + ", version=" + version + "]";
	}
}