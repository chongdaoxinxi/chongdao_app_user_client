package com.chongdao.client.entitys.coupon;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-06-18 15:27
 * 优惠券叠加规则表类
 */
@Entity
@Getter
@Setter
@Table(name = "cpn_superposition_rule")
@DynamicUpdate
public class CpnSuperpositionRule {

    /**
     * 说明：
     * 与主表cpn_info通过cpn_id关联，是多对一关系。
     * spn_cpn_type字段存放优惠券类型（1现金券(红包) 2满减券 3折扣券），
     * 与主表类型字段指对应，表示着cpn_id对应的优惠券，可以与spn_cpn_type类型的优惠券叠加适用，
     * 注意，需要两张不同券的叠加规则都包含对方的时候，该两种券才代表能叠加使用。例如，A现金券和B满减券，
     * A现金券的叠加规则是可以与满减券叠加使用，必须要B满减券的叠加规则也包含现金券，这样A和B才能叠加使用
     * 另:
     * 优惠券叠加使用必须坚持一个门槛校验的优先级原则，可以业务上决定各类型券的优先级，
     * 一般比较合理的优先级是：折扣券>现金券>满减券。
     * 也就是说，要叠加使用优惠券，必须先计算使用折扣券的券后金额，再校验金额是否达到现金券的门槛金额，
     * 满足后又取现金券的券后金额再检验满减券的门槛金额。
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cpnId;          //优惠券ID

    private Integer spnCpnType;     //可叠加优惠券类型：1现金券(红包) 2满减券 3折扣券 ->(主表 cpn_type类型一致)

    private Integer spnCount;       //可叠加次数 >0

    private Date createDate;        //创建时间

    private Date updateDate;        //更新时间

    private Integer isDelete;       //是否删除 0否 1是





}
