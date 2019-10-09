package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderEvalVO {

    private Integer id;

    @NotNull(message = "userId不能为空")
    private Integer userId;

    @NotNull(message = "shopId不能为空")
    private Integer shopId;

    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    @NotEmpty(message = "商家评价内容不能为空")
    private String shopContent;

    @NotNull(message = "配送员评价星级不能为空")
    private Double expressGrade;

    @NotEmpty(message = "配送员评价内容不能为空")
    private String expressContent;
    //店铺评分
    @NotNull(message = "商家评价星级不能为空")
    private Double shopGrade;

    @NotNull(message = "expressId不能为空")
    private Integer expressId;

    private String shopImg;

    private String expressImg;

    private String userName;



    //店铺准时率
    private BigDecimal shopPunctuality;

    private String goodsName;

    private Date createTime;

    private Date updateTime;

    //配送员
    private String expressName;

    private String shopName;

    private String logo;



    private Integer shopEnabledAnonymous; //匿名 0 否 1是

    private Integer expressEnabledAnonymous;

    @Transient
    private String token;


    @Override
    public String toString() {
        return "OrderEvalVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shopId=" + shopId +
                ", orderNo='" + orderNo + '\'' +
                ", shopContent='" + shopContent + '\'' +
                ", expressGrade=" + expressGrade +
                ", expressContent='" + expressContent + '\'' +
                ", shopGrade=" + shopGrade +
                ", expressId=" + expressId +
                ", shopImg='" + shopImg + '\'' +
                ", expressImg='" + expressImg + '\'' +
                ", userName='" + userName + '\'' +
                ", shopPunctuality=" + shopPunctuality +
                ", goodsName='" + goodsName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", expressName='" + expressName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", logo='" + logo + '\'' +
                ", shopEnabledAnonymous=" + shopEnabledAnonymous +
                ", expressEnabledAnonymous=" + expressEnabledAnonymous +
                '}';
    }
}
