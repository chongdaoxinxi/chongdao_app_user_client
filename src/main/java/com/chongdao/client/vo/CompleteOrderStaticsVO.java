package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class CompleteOrderStaticsVO {
    private Date startDate;
    private Date endDate;
    private Integer totalCount;
    private BigDecimal totalMoney;
    private List<CompleteOrderStaticsSingleVO> list;
}
