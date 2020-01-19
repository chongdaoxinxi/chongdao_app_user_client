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
 * @Description 订单相关宠物卡片信息
 * @Author onlineS
 * @Date 2020/1/16
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class OrderPetInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String orderNo;
    private Integer petCardId;
    private Date createTime;
}
