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
    //投保人信息
    private Integer userId;//用户id
    private String name;
    private String cardType;//01:身份证;02:户口本;03:护照;04:军人证件;05:驾驶执照;06:返乡证;07:港澳身份证;08:工号;09:赴台通行证;10:港澳通行证;15:士兵证;16:外国人永久通行证;25:港澳居民来往内地通行证;26:台湾居民来往内地通行证;31:组织机构代码;37:统一社会信用代码;99:其他;
    private String cardNo;
    private String phone;

    //被保人信息
    private Integer MedicalInsuranceRecognizeeId;//被保人id->指向被保人表

    private String rationType;//方案代码-用户所选保险及方案

    //宠物属性
    private Integer petCardId;//宠物卡片id
    private String petPhoto;//宠物图片->用于审核
    private Integer medicalInsuranceShopChipId;//选择的宠物芯片的id

    //订单相关属性
    private Integer status;//订单状态
    private Date applyTime;//保险订单下单时间
    private Date auditTime;//审核完成时间
    private Date createTime;//数据创建时间

    //分销
    private String recommendCode;//推广码
}
