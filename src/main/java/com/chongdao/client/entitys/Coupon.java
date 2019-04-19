package com.chongdao.client.entitys;

import com.chongdao.client.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author onlineS
 * @Description 配送券
 * @Date 17:34 2019/4/18
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name="coupon")
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer shopId;
    private Integer categoryId;
    private String couponName;
    private BigDecimal fullPrice;//起效金额
    private BigDecimal decreasePrice;//减免金额
    private Integer type;//0:满减券(店铺活动),3:优惠券,2满减优惠券
    private Integer usedCouponCount;//优惠券数量
    private Integer receiveCouponCount;//已领取优惠券数量
    //满减优惠券状态 -1删除，0上架，1下架（默认为0）
    private Integer status = ResponseCode.UP_COUPON.getStatus();
    private String activeTime;//有效时间
    private String missTime;//失效时间
    private Date createTime;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }
}
