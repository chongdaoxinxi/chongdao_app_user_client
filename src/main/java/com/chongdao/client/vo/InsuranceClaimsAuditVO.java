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
 * @Date 2019/10/16
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceClaimsAuditVO {
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
    private BigDecimal insuranceTotalFee;//医疗费用
    private Date createTime;
    private Date updateTime;
    private String insuranceOrderNo;//保险订单号
    private BigDecimal sumAmount;
    private BigDecimal sumPremium;
    private String name;
    private String phone;
    private String claimsConfirmation;//理赔确认书
    private String claimsConfirmationFileName;//理赔确认书文件名
    private String adminOpinion;
    private String insuranceOpinion;
}
