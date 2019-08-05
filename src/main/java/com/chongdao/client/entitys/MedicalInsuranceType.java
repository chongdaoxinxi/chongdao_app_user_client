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

/**
 * @Description 医疗保险种类
 * @Author onlineS
 * @Date 2019/8/3
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicalInsuranceType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer category;//1:医疗;2:家庭责任;3:运输险;
    private String plan;//方案
    private BigDecimal sumAmount;//保险总保金额
    private String responsibility;//保险责任
    private Integer timeLimit;//期限(多少个月)
    private Integer unexpectWaitingPeriod;//意外等待期(多少天)
    private Integer diseaseWaitingPeriod;//疾病类等待期(多少天
    private BigDecimal simpleMaxCompensation;//单次赔偿最高(元)
    private BigDecimal simpleCompensationDeduction;//单次赔偿免赔(元)
    private Integer compensationPercent;//赔偿比例(1-100)
    private BigDecimal sumPremium;//保单总保险费-用户付费
}
