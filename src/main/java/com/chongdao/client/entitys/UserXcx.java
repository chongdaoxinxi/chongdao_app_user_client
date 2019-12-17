package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description 小程序老用户
 * @Author onlineS
 * @Date 2019/12/17
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserXcx implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String phone;
}
