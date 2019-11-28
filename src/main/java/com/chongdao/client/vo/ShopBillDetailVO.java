package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/11/28
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ShopBillDetailVO {
    private Integer status;
    private Date createTime;
    private BigDecimal price;//收款金额
    private BigDecimal orderPrice;//订单金额
    private String note;
    private String orderNo;
}
