package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 医疗保险医疗就诊记录
 * @Author onlineS
 * @Date 2019/8/7
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class InsuranceFeeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderNo;
    private Integer userId;//用户id
    private Integer shopId;//商店id
    private Integer insuranceId;//医疗保险订单id
    private String comment;//说明
    private BigDecimal money;//医疗费用
    private String photo;//照片
    private Integer status;// -1: 待支付; 1:已支付
    private Date createTime;
    private Date paymentTime;
    private Integer paymentType;//1: 支付宝支付, 2: 微信app支付, 3:微信小程序支付

    @Transient
    private String userName;
    @Transient
    private String shopName;
}
