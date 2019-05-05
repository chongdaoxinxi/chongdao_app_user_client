package com.chongdao.client.entitys;

import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.ResultEnum;
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
 * @Description 配送券
 * @Date 17:34 2019/4/18
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="coupon")
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer shopId;
    private Integer categoryId; //商品分类id（1，商品类，2，服务类）
    private String couponName;
    private BigDecimal fullPrice;//起效金额
    private BigDecimal decreasePrice;//减免金额
    private Integer type;//0:满减券(店铺活动),2:优惠券
    private Integer usedCouponCount;//优惠券数量
    private Integer receiveCouponCount;//已领取优惠券数量
    //满减优惠券状态 -1删除，0上架，1下架（默认为0）
    private Integer status = CouponStatusEnum.UP_COUPON.getStatus();
    private String startTime;//有效时间
    private String endTime;//失效时间
    private Date createTime;
    private Date updateTime;

    @Transient
    private Integer cardId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }
}
