package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/30
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOrderStaticsSingleVO {
    private String name;
    private String count;
    private BigDecimal money;
}
