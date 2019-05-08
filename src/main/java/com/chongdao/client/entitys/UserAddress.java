package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;

    private String userName;

    private String tel;

    private String lat;

    private String lng;

    private String location;

    private String address;

    private String isDefaultAddress;

    private Date createTime;

    private Date updateTime;

    public UserAddress(Integer id, Integer userId, String userName, String tel, String lat, String lng, String location, String address, String isDefaultAddress, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.tel = tel;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
        this.address = address;
        this.isDefaultAddress = isDefaultAddress;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


}