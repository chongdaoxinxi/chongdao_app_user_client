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
 * @Description 家庭责任险理赔
 * @Author onlineS
 * @Date 2019/8/14
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class FamilyInsuranceClaims implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer familyInsuranceOrderId;//医疗险订单ID
    private String otherMaterials;//其他材料
    private Integer auditStatus;//审核状态; -1: 拒绝;0:待审核;1;平台审核通过;2:保险公司审核通过;3:理赔完成;
    private Date createTime;
    private Date updateTime;
}
