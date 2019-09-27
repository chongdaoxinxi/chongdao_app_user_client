package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author fenglong
 * @date 2019-09-26 17:48
 */
@Getter
@Setter
public class CpnInfoVo {
    private Integer cpnId;
    //优惠券类型 1现金券 2满减券 3折扣券 4店铺满减
    private Integer cpnType;

    //优惠券名称
    private String cpnName;

    private Integer shopId;
    //面值 XX元/满XX元/XX折
    private BigDecimal cpnValue;
    //有效期开始时间
    private String validityStartDate;
    //有效期结束时间
    private String validityEndDate;

    private String shopName;

    //适用范围类型 1全场通用 2限品类(暂定) 3限商品 4限服务 5配送单程
    // 6配送双程 7仅限服务（配送单程） 8仅限服务（双程） 9 仅限商品（配送单程） 10仅限商品（配送双程）
    private Integer scopeType;

    private String scopeName;

    private String scopeTypeShopId;

}
