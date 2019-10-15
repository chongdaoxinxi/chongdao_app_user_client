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
 * @Description 用户提现
 * @Author onlineS
 * @Date 2019/10/15
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithdrawal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private String name;
    private BigDecimal money;
    private BigDecimal realMoney;
    private String applyNote;//申请备注
    private String checkNote;//审核备注
    private Integer status;//-1:拒绝;0:审核中;1:审核通过
    private Date checkTime;//审核时间
    private Date createTime;
    private Date updateTime;
}
