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
    private String weeks;
    private Integer totalCount;
    private BigDecimal totalMoney;
    private Date startDate;
    private Date endDate;
    private List<CompleteOrderStaticsSingleVO> list;

    public CompleteOrderStaticsVO(String weeks, Integer totalCount, BigDecimal totalMoney, Date startDate, Date endDate) {
        this.weeks = weeks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCount = totalCount;
        this.totalMoney = totalMoney;
    }
}
