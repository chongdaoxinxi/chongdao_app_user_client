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
 * @Description 医疗险
 * @Author onlineS
 * @Date 2019/5/28
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicalInsuranceOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;//用户id
    private String name;
    private String cardType;
    private String cardNo;
    private String phone;

    private Integer medicalInsurancePolicyHolderId;//投保人id
    private Integer petCardId;//宠物卡片id
    private String petPhoto;//宠物图片->用于审核
    private Integer medicalInsuranceShopChipId;//选择的宠物芯片的id
    private Integer status;//订单状态
    private Date applyTime;//保险订单下单时间
    private Date auditTime;//审核完成时间
    private Date createDate;//数据创建时间
    private String recommendCode;//推广码
}
