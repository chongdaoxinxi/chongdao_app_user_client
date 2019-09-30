package com.chongdao.client.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the user database table.
 * 
 */
@Setter
@NoArgsConstructor
@Getter
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

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

	
}