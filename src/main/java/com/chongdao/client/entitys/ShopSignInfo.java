package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
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
 * @Description 商家入驻信息表
 * @Author onlineS
 * @Date 2019/12/27
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopSignInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;//门店名称
    private String branchName;//分店名称
    private String frontPhoto;//门脸照
    private String accordPhoto;//店内照
    private String logo;//门店logo
    private String areaId;//区域id
    private String areaCode;//区域码
    private String address;//店铺地址
    private Integer type;//店铺类型, 0:普通店铺, 1:官方店铺, 2:医院类店铺, 3:医保类店铺, 4:活体类店铺
    private String acceptPhone;//接单电话
    private String managePhone;//老板电话
    private String manageName;//老板姓名
    //资质证书等
    private String businessLicense;//营业执照
    private String enterPlatformProtocol;//入驻平台协议书
    private String dogFarmingLicense;//狗类养殖许可证, 活体类需要
    private String animalMedicalLicense;//动物医疗许可证, 医院/医保类需要
    //身份证信息
    private String idCardFrontPhoto;//身份证正面
    private String idCardReversePhoto;//身份证反面
    private String idCardHandFrontPhoto;//手持身份证照片

    private Integer shopId;//关联shop表id
    private Integer status;//-1: 已拒绝; 0: 待审核; 1:审核通过
    private Date createTime;
    private Date updateTime;
}
