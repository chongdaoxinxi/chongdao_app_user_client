package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class OrderEval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer shopId;

    private String orderNo;

    private String content;

    private Integer grade;

    private Integer status;

    private String img;

    private Date createTime;

    private Date updateTime;

    public OrderEval(Integer id, Integer userId, Integer shopId, String orderNo, String content, Integer grade, Integer status, String img, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.shopId = shopId;
        this.orderNo = orderNo;
        this.content = content;
        this.grade = grade;
        this.status = status;
        this.img = img;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

}