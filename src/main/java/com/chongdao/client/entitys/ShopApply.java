package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 商家提现
 * @Date 9:10 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopApply extends PageParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer shopId;
    private BigDecimal applyMoney;//申请提现金额
    private String applyNote;//申请备注
    private Date checkTime;//审核时间
    private String checkNote;//审核备注
    private BigDecimal realMoney;//实际提现金额
    private Integer status;//-1:拒绝;0:待审核;1:通过;
    private Date createTime;//创建时间
    private Date updateTime;
    @Transient
    private String shopName;//商店名称
    private Integer deductRate;//提现扣费比例
    @Transient
    private String phone;
}