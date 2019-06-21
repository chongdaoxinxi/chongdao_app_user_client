package com.chongdao.client.entitys.coupon;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-06-18 15:12
 * 优惠券适用范围表类
 */
@Entity
@Getter
@Setter
@Table(name = "cpn_scope_rule")
@DynamicUpdate
public class CouponScopeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cpnId;      //优惠券id

    /**
     * 注意：主表的scope_type字段表示适用范围的类型，
     * 它决定着适用范围表中kind_id存放什么ID，kind_id是一个业务ID，
     */
    private Integer kindId;     //业务（商品）ID （商品ID/类目ID/...）

    private Integer scopeType;  //适用范围类型 1全场通用 2限品类(暂定) 3限商品 4限服务 5配送单程 6配送双程

    private Integer categoryId;   //商品类型 (商品、服务，根据业务场景传入的不同)

    private String kindName;    //业务名称

    private Date createDate;     //创建时间

    private Date updateDate;     //更新时间

    private Integer isDelete;    //是否删除 0否 1是





 }
