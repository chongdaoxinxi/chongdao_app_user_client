package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 推广奖励记录
 * @Author onlineS
 * @Date 2019/8/7
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
@Entity
public class RecommendRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;//消费人id
    private Integer recommenderId;//推荐人id(用户/配送员/商家)
    private Integer type;//推荐人类型(用户/配送员/商家)
    private Integer recommendType;//1:新用户推广;2:医疗险;3:家责险;
    private Integer consumeId;//用户消费id(订单/医疗险/家责险)
    private BigDecimal consumeMoney;//消费金额
    private BigDecimal rewardMoney;//奖励金额
    private Integer rewardPercent;//奖励比例
    private Integer isRefund;//是否发生过退款
    private Date createTime;
    private Date updateTime;
}
