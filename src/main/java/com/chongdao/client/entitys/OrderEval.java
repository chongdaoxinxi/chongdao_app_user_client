package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
public class OrderEval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "userId不能为空")
    private Integer userId;

    @NotNull(message = "shopId不能为空")
    private Integer shopId;

    @NotEmpty(message = "订单号必填")
    private String orderNo;

    @NotEmpty(message = "评价内容必填")
    private String content;

    private Double grade; //评价等级

    private Integer status = 1;

    private String img; //图片地址

    private Integer enabledAnonymous; //匿名 0 否 1是

    private Date createTime;

    private Date updateTime;


    public OrderEval( Integer id,Integer userId,  Integer shopId, String orderNo,  String content, Double grade, Integer status, String img, Integer enabledAnonymous, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.shopId = shopId;
        this.orderNo = orderNo;
        this.content = content;
        this.grade = grade;
        this.status = status;
        this.img = img;
        this.enabledAnonymous = enabledAnonymous;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderEval() {
    }
}