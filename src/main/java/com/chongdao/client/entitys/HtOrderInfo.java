package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-17 15:39
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class HtOrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //活体订单号
    private String htOrderNo;

    //买家id
    private Integer buyerUserId;

    //卖家id
    private Integer sellUserId;

    //订单类型 1：活体 2领养
    private Integer orderType;

    //服务类型：2 单程 3到店自取
    private Integer serviceType;

    //接地址id
    private Integer receiveId;

    //接宠时间
    private Date receiveTime;

    //活体id
    private Integer livingId;

    //配送券id
    private Integer serviceId;

    //配送费
    private BigDecimal servicePrice;

    //参考orderStatusEnum类
    private Integer orderStatus;

    //实际付款
    private BigDecimal payment;

    //订单总价
    private BigDecimal totalPrice;

    //折扣价
    private BigDecimal discountPrice;

    //支付方式 1 支付宝 2微信
    private Integer payType;

    //备注
    private String remark;

    private Date createTime;

    private Date updateTime;

    private String areaCode;

}


