package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 地区账户提现申请
 * @Author onlineS
 * @Date 2019/6/26
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AreaWithdrawalApply implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer managementId;
    private BigDecimal applyMoney;//申请提现金额
    private String applyNote;//申请备注
    private Date checkTime;//审核时间
    private String checkNote;//审核备注
    private BigDecimal realMoney;//实际提现金额
    private Integer status;//-1:拒绝;0:待审核;1:通过;
    private Date createTime;//创建时间
    private Date updateTime;

    @Transient
    private String areaName;
}
