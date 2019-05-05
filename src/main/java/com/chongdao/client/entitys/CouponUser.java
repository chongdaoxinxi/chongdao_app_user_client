package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 关联优惠券中间表
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class CouponUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**  优惠券id */
    private Integer couponId;

    /**  商店id */
    private Integer shopId;

    /**  用户id */
    private Integer userId;

    /** 领取状态：0代表未领取，1代表已领取 */
    private Integer receiveStatus;

    private Date createTime;

    private Date updateTime;
}
