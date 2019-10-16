package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author fenglong
 * @date 2019-10-16 13:54
 */
@Entity
@Getter
@Setter
public class CpnParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //参数名称如优惠券类型名称、适用范围名称
    private String name;

    //类型：1.优惠券类型 2.优惠券适用范围
    private Integer type;

    //注：cpnType和scopeType 不会同时存在
    //优惠券类型 1现金券 2满减券 3折扣券 4店铺满减
    private Integer cpnType;

    //1全场通用 2限品类(暂定) 3限商品 4限服务 5配送单程
    //    // 6配送双程 7仅限服务（配送单程） 8仅限服务（双程） 9 仅限商品（配送单程） 10仅限商品（配送双程）
    private Integer scopeType;

    //区分商家可使用的类型：0 商家 1管理员（管理可看全部）
    private Integer paramType;
}
