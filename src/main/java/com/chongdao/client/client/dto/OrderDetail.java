package com.chongdao.client.client.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@NoArgsConstructor
@Getter
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer count;

    private Double discount;


    private String goodsName;

    private String icon;

    private Integer orderId;

    private BigDecimal price = BigDecimal.ZERO;

    private Integer reOrderId;

    private Integer shopId;

    private Integer type;

    private Integer typeId;

    private String unit;
    private String orderNoRe;

    private Integer status;



}
