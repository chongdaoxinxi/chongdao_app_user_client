package com.chongdao.client.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2020/1/2
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class PickupInsuranceVO {
    private String orderNo;//保险订单号
    private String petName;//宠物名称
    private String petBreedName;//宠物品种名称
    private BigDecimal petAge;//宠物年龄
    private String policyUrl;//电子单证链接
}
