package com.chongdao.client.entitys.coupon;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-06-18 15:37
 * 优惠券门槛规则表类
 */
@Entity
@Getter
@Setter
@Table(name = "cpn_threshold_rule")
@DynamicUpdate
public class CpnThresholdRule {

    /**
     * 与主表cpn_info通过cpn_id关联，是多对一关系。
     * min_price最小金额和max_price最大金额，是指优惠券适用的金额范围，均为非必填字段，但必须至少有一个有值。
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cpnId;          //优惠券ID

    private BigDecimal minPrice;    //门槛最低金额

    private BigDecimal maxPrice;    //门槛最高金额;

    private Date createDate;        //创建时间

    private Date updateDate;        //更新时间

    private Integer isDelete;       //是否删除 0否 1是
}
