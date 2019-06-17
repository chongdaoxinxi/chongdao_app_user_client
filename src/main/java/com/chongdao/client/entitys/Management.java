package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 管理员
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@Entity
@Setter
@NoArgsConstructor
@Getter
public class Management implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
    private String password;
    private String icon;
    private String phone;
    private String mail;
    private Integer status;
    private Integer type;
    private Integer level;
    private Date createDate;
}
