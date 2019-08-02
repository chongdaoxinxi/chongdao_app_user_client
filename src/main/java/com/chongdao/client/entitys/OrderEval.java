package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderEval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "userId不能为空")
    private Integer userId;

    @NotEmpty(message = "shopId不能为空")
    private Integer shopId;

    @NotEmpty(message = "订单号必填")
    private String orderNo;

    @NotEmpty(message = "评价内容必填")
    private String content;

    private Integer grade; //评价等级

    private Integer status;

    private String img; //图片地址

    private Integer enabledAnonymous; //匿名 0 否 1是

    private Date createTime;

    private Date updateTime;

    @Transient
    private String token;

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