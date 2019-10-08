package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/9/30
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
public class RecommendRankVO {
    private String name;
    private BigDecimal money;
}
