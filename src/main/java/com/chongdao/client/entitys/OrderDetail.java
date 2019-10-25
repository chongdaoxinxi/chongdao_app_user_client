package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.simpleframework.xml.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String orderNo;

    private Integer count;

    private Integer goodId;

    private Integer orderId;

    private String name;

    private BigDecimal price;

    private String icon;

    private BigDecimal currentPrice;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private BigDecimal totalPrice;

    private String reOrderNo;

    @Transient
    private String shopName;

    @Transient
    private String shopLogo;

    @Transient
    private String shopPhone;

    public OrderDetail(Integer id, String orderNo, Integer count, Integer goodId, Integer orderId, String name, BigDecimal price, String icon, BigDecimal currentPrice, Date createTime, Date updateTime, Integer userId, BigDecimal totalPrice,String reOrderNo) {
        this.id = id;
        this.orderNo = orderNo;
        this.count = count;
        this.goodId = goodId;
        this.orderId = orderId;
        this.name = name;
        this.price = price;
        this.icon = icon;
        this.currentPrice = currentPrice;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.reOrderNo = reOrderNo;
    }


}