package com.chongdao.client.entitys.coupon;

import com.chongdao.client.utils.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-06-18 14:44
 * 优惠券主表类
 */
@Entity
@Getter
@Setter
@Table(name = "cpn_info")
@DynamicUpdate
public class CouponInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer shopId;  //店铺id

    private String scopeShopId;  //限制店铺使用范围

    private String batchId;  //批次id；可选

    private String cpnCode;  //优惠券编码;可选

    private String cpnName;  //优惠券名称

    private BigDecimal cpnValue; //优惠券面值：面值 XX元/满XX元/XX折

    private Integer cpnType; //优惠券类型 1现金券 2满减券 3折扣券 4 店铺满减 5 公益券

    private Integer cpnState = 0; //状态  -1 已作废 0待发布 1已发布 2已下架

    private Integer cpnStock; //库存（张数）

    private String cpnDesc;   //描述

    private Integer ruleType;  //门槛规则 0 无门槛 1有门槛

    private Integer scopeType; //适用范围类型 1全场通用 2限品类(暂定) 3限商品 4限服务 5配送单程 6配送双程 7仅限服务（配送单程） 8仅限服务（双程） 9 仅限商品（配送单程） 10仅限商品（配送双程）

    private Integer validityType;    //有效期类型 1绝对有效期（XX年XX月XX日 - XX年XX月XX日）2 相对有效期（XX天内有效）

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validityStartDate;  //绝对有效期-开始时间

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validityEndDate;   //绝对有效期-结束时间

    private Integer validityDays;   //相对有效期-XX天

    private Integer gainCount;    //每个用户可领取次数 0 不限 >0 次数

    private Integer gainMethod;   //用户获取方式 1需要用户主动领取 2直接发放到用户账户

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createDate;      //创建时间

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateDate;      //更新时间

    private Integer useNumber;    //使用人数

    private Integer gainNumber;   //领取人数

    @Transient
    private BigDecimal minPrice;  //最低门槛金额

    @Transient
    private BigDecimal maxPrice;  //最高门槛金额

    @Transient
    private String scopeTypeName; //适用范围类型名称

    @Transient
    private Integer categoryId;   //类型id（商品、服务）

    @Transient
    private Integer goodsId;      //商品id

    @Transient
    private String token;

    @Transient
    private Integer enabled = 0;    //是否可用 0否 1是


}
