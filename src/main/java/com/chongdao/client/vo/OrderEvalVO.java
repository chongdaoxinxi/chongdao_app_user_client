package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderEvalVO {

    private Integer id;

    private Integer userId;

    private Integer shopId;

    private String orderNo;

    private String content;

    private Integer grade;

    private String img;

    private String userName;

    //店铺评分
    private Double shopGrade;

    //店铺准时率
    private BigDecimal shopPunctuality;

    private String goodsName;

    private Date createTime;

    private Date updateTime;

    //配送员
    private String expressName;

    private String shopName;

    private Integer expressId;

    private Integer enabledAnonymous; //匿名 0 否 1是
}
