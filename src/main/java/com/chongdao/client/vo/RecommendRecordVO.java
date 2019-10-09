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
 * @Date 2019/10/9
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendRecordVO {
    private String userName;
    private String icon;
    private Integer recommendType;//推荐人类型(用户/配送员/商家)
    private Integer consumeType;//1:医疗险;2:家责险;3:新用户推广;
    private BigDecimal consumeMoney;//消费金额
    private Integer rewardPercent;//奖励比例
    private BigDecimal rewardMoney;//奖励金额
    private Integer isRefund;//是否发生过退款, 1:是, -1:否
    private Date createTime;
}
