package com.chongdao.client.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the order_address database table.
 * 
 */
@Setter
@NoArgsConstructor
@Getter
public class OrderAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String address;

	private Double lat;

	private Double lng;

	private Integer orderId;

	private String phone;

	private Integer type;

	private String userName;


}