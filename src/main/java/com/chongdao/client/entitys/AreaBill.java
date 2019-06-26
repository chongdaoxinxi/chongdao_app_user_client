package com.chongdao.client.entitys;

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
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/26
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AreaBill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer shopId;
    private Integer orderId;
    private BigDecimal price;
    private String note;
    private Integer type;//1:客户订单, 2:订单退款, 3:我的提现
    private Date createTime;
}
