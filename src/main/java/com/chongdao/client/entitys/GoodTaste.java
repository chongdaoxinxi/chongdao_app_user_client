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
 * @Description 口味(狗粮/猫粮)
 * @Author onlineS
 * @Date 2019/9/28
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class GoodTaste implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String label;
    private Date createTime;
}
