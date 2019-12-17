package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description 支付凭证实体
 * @Author onlineS
 * @Date 2019/12/16
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVoucherVO {
    private String shopName;
    private String payType;
    private String createTime;
    private String orderNo;
    private BigDecimal money;
    private String remark;
}
