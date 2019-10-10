package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 医疗保险理赔
 * @Author onlineS
 * @Date 2019/7/24
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceClaims implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer medicalInsuranceOrderId;//医疗险订单ID
    private String diagnosticProof;//诊断证明
    private String costList;//费用清单
    private String payCredentials;//支付凭证
    private String otherMaterials;//其他材料
    private String petPhotoFlank;//侧面宠物照片
    private String petPhotoFront;//正面面宠物照片
    private String petPhotoReverse;//反面宠物照片
    private String bankCardPhoto;//银行卡照片
    private String bankCardNo;//银行卡号
    private Integer auditStatus;//审核状态; -2: 拒绝;-1:已保存;0:待审核;1;平台审核通过;2:保险公司审核通过;3:等待用户确认理赔金额;4:用户确认理赔金额;5:理赔完成;
    private BigDecimal money;//理赔金额
    private Date createTime;
    private Date updateTime;

    @Transient
    private String petName;//宠物姓名
    @Transient
    private String petPhoto;//宠物图片
    @Transient
    private String petBreedName;//宠物品种名称
    @Transient
    private BigDecimal petAge;//宠物年龄
}
