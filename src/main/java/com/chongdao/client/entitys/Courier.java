package com.chongdao.client.entitys;



import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 快递员实体
 *
 * @author 杨荣富
 * @date 2018/07/25
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="courier")
public class Courier extends PageParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String realName;

    private String userName;

    private String password;

    private Integer status;

    private String areaCode;
    private Double lastLng;

    private Double lastLat;
    private String headImg;

    private String openId;
    @Transient
    private String areaName;

}