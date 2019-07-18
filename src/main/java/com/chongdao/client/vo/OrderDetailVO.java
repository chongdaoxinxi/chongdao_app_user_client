package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author fenglong
 * @date 2019-07-17 17:48
 */
@Getter
@Setter
public class OrderDetailVO {
    private String goodsName;
    private Integer quantity;
    //当前价(折扣)
    private BigDecimal currentPrice;
    //原价
    private BigDecimal originPrice;

}
