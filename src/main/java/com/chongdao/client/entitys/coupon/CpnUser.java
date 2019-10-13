package com.chongdao.client.entitys.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-06-18 15:43
 * 用户领取优惠券表类
 */
@Entity
@Getter
@Setter
@Table(name = "cpn_user")
@DynamicUpdate
public class CpnUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;              //用户ID

    private Integer cpnId;               //优惠券ID

    private String shopId;

    private String cpnCode;              //优惠券编码;可选

    private String cpnBatchId;           //优惠券批次ID

    private Integer cpnType;             //优惠券类型 1现金券 2满减券 3折扣券 4店铺满减 5 公益券

    private BigDecimal cpnValue;         //面值 XX元/满XX元/XX折

    private Integer cpnScopeType;        //适用范围类型 1全场通用 2限品类 3限商品

    private Integer userCpnState;        //用户优惠券状态 0未使用 1已使用 2已过期

    private Integer count;               //优惠券数量

    private Date useTime;                //使用时间

    private String useDesc;              //使用说明

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validityStartDate;      //有效期开始时间

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validityEndDate;        //有效期结束时间

    private Date gainDate;               //领取时间

    private String gainDesc;             //获取说明

    private Date createDate;             //创建时间

    private Date updateDate;             //更新时间

    private Integer isDelete;            //是否删除 0否 1是

    private Integer ruleType;

    @Transient
    private Integer enabled = 0;    //是否可用 0否 1是


    @Transient
    private Integer receive = 0; //是否领取 0否 1是


}
