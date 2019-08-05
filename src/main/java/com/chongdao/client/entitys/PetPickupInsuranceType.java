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
 * @Description 运输险种类
 * @Author onlineS
 * @Date 2019/8/3
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class PetPickupInsuranceType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String plan;//方案
    private BigDecimal deathOrMaimCompensation;//死亡/伤残赔偿
    private BigDecimal lostCompensation;//走失赔偿
    private BigDecimal medicalCompensation;//医疗赔偿
    private BigDecimal compensationDeduction;//免赔金额
    private BigDecimal compensationDeductionPercent;//免赔比例
    private BigDecimal sumPremium;//保单总保险费-用户付费
}
