package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 医疗保险报销
 * @Author onlineS
 * @Date 2019/5/30
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicalInsuranceReimbursement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer medicalInsuranceOrderId;//医疗保险订单id
    private Integer status;//报销流程状态
    private Date applyDate;//报销申请时间
    private Date auditDate;//审核完成时间
    private Date createDate;//创建时间
}
