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
 * @Date 2019/9/6
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class ManagementTrans implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer managementId;
    private BigDecimal money;
    private Integer type;//1:订单入账;2:订单退款;3:账户提现;
    private String comment;
    private Date createTime;
}
