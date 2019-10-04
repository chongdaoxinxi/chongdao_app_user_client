package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-12 12:42
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class LivingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "userId不能为空")
    private Integer userId;
    //宠物类别名称
    @NotEmpty(message = "宠物类别名称不能为空")
    private String petTypeName;
    //宠物类别id
    @NotEmpty(message = "petTypeId不能为空")
    private Integer petTypeId;
    //品种
    @NotEmpty(message = "品种不能为空")
    private String breed;
    //品种id
    @NotEmpty(message = "breedId不能为空")
    private Integer breedId;
    //性别
    @NotEmpty(message = "性别不能为空")
    private String sex;
    //血统
    @NotEmpty(message = "血统不能为空")
    private String blood;
    //标题
    @NotEmpty(message = "标题不能为空")
    private String title;
    //价格
    @NotEmpty(message = "价格不能为空")
    private BigDecimal price;
    //所在地
    @NotEmpty(message = "所在地不能为空")
    private String address;
    //是否30天赔付(0 否 1 是)
    @NotEmpty(message = "请选择是否30天赔付")
    private Integer enabledPay;
    //赠送保险（0 否 1是）
    @NotEmpty(message = "请选择是否赠送保险")
    private Integer enabledInsurance;
    //保险方案
    @NotEmpty(message = "保险方案不能为空")
    private String insuranceScheme;
    //方案id
    @NotEmpty(message = "schemeId不能为空")
    private Integer schemeId;
    //微信二维码
    private String wxCode;
    //联系电话
    @NotEmpty(message = "联系电话不能为空")
    private String phone;
    //宠物照片详情
    @NotEmpty(message = "宠物照片不能为空")
    private String petImgUrl;
    //宠物昵称（领养必填）
    private String nickName;
    //活体类型（0 活体 1 领养）
    @NotEmpty(message = "请选择活体类型：0 活体，1领养 ，2.寻宠")
    private Integer type;
    //点赞个数
    private Integer supportCount;
    //默认上架(0 下架 -1 删除 1 上架)
    private Integer status = 1;

    //走失时间
    private Date lostTime;
    //走失地址
    private String lostAddress;
    private Date createTime;
    private Date updateTime;


    @Transient
    @NotEmpty(message = "token不能为空")
    private String token;

    @Transient
    private String userName;

    @Transient
    private String icon;


}
