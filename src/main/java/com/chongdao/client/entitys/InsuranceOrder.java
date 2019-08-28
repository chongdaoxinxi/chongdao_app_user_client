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
 * @Description 医疗险
 * @Author onlineS
 * @Date 2019/5/28
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class InsuranceOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String insuranceOrderNo;

    //投保人信息
    private Integer userId;//用户id
    private String name;
    private String cardType;//01:身份证;02:户口本;03:护照;04:军人证件;05:驾驶执照;06:返乡证;07:港澳身份证;08:工号;09:赴台通行证;10:港澳通行证;15:士兵证;16:外国人永久通行证;25:港澳居民来往内地通行证;26:台湾居民来往内地通行证;31:组织机构代码;37:统一社会信用代码;99:其他;
    private String cardNo;
    private String phone;
    private Integer isSendMsg;//是否发送短信给投保人, 默认为1

    private String idCardFrontPhoto;//投保人身份证正面照片
    private String idCardReversePhoto;//投保人身份证反面照片
    private String bankCardPhoto;//银行卡照片

    //被保人信息
    private Integer acceptSeqNo;//被保人序列号, 默认为1
    private String acceptName;
    private String acceptCardType;//01:身份证;02:户口本;03:护照;04:军人证件;05:驾驶执照;06:返乡证;07:港澳身份证;08:工号;09:赴台通行证;10:港澳通行证;15:士兵证;16:外国人永久通行证;25:港澳居民来往内地通行证;26:台湾居民来往内地通行证;31:组织机构代码;37:统一社会信用代码;99:其他;
    private String acceptCardNo;
    private String acceptPhone;
    private String acceptMail;

    //受益人
    private Integer beneficiary;//默认为1; 即投保人自己

    private String rationType;//方案代码-用户所选保险及方案
    private Integer insuranceType;//保险类型, 1:宠物医疗, 2:家庭责任, 3:运输险

    //宠物属性(宠物即被保人)
    private Integer petCardId;//宠物卡片id
    private String petPhoto;//宠物图片->用于审核
    private Integer medicalInsuranceShopChipId;//选择的宠物芯片的id

    private String orderNo;//运输险, 订单id

    //订单相关属性
    private Integer status;//订单状态(0:保存;1:待支付;2:已支付待一级审核;3:待二级审核;4:待生成保单;4:保单生成;)
    private Date createTime;//数据创建时间
    private Date applyTime;//保险订单下单时间(付款时间/投保日期)
    private Date auditTime;//审核完成时间(平台投保审核完成日期)
    private Date signBillTime;//签单日期(即与保险公司交互后生成保险单号的时间)

    private Date insuranceEffectTime;//保单起保日期(生效日期)(默认为当前时间往后推24小时的第一个零点, 用户可自主选择30天内的任何一个零点)
    private Date insuranceFailureTime;//保单终保日期(失效截止日期)

    //保单相关属性
    private BigDecimal sumAmount;//保额
    private BigDecimal sumPremium;//保费

    private String policyDownloadUrl;//电子保单下载地址
    private String policyImage;//电子保单图片

    //分销
    private String recommendCode;//推广码

    //测试使用
    private String petName;
    private String typeName;
    private String birthDate;
    private String age;
    private String petCardType;
    private String petCardNo;
    private String createTimeStr;
}
