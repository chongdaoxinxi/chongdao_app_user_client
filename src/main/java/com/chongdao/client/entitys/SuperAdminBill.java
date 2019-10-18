package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author onlineS
 * @Description 主账户资金流水记录(超级管理员)
 * @Date 9:20 2019/4/19
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SuperAdminBill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer shopId;
    private Integer orderId;
    private BigDecimal price;
    private String note;
    private Integer type;//1:订单消费, 2:订单退款, 3:我的提现
    private String areaCode;
    private Date createTime;
}
