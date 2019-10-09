package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description 保险相关待办
 * @Author onlineS
 * @Date 2019/10/9
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceTodoVO {
    Integer id;
    BigDecimal money;
    String core;
}
