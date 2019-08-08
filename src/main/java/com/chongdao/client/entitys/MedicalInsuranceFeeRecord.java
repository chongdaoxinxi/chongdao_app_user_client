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
 * @Description 医疗保险医疗就诊记录
 * @Author onlineS
 * @Date 2019/8/7
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
@Entity
public class MedicalInsuranceFeeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user_id;//用户id
    private Integer shop_id;//商店id
    private Integer insurance_id;//医疗保险订单id
    private BigDecimal money;//医疗费用
    private String photo;//照片
    private Date createTime;
}
