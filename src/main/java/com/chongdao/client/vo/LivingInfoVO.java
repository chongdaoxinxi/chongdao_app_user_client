package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-16 15:22
 */
@Getter
@Setter
public class LivingInfoVO {

    private Integer id;

    private Integer userId;
    //宠物类别名称
    private String petTypeName;
    //宠物类别id
    private Integer petTypeId;
    //品种
    private String breed;
    //品种id
    private Integer breedId;
    //性别
    private String sex;
    //血统
    private String blood;
    //标题
    private String title;
    //价格
    private BigDecimal price;
    //所在地
    private String address;
    //是否30天赔付(0 否 1 是)
    private Integer enabledPay;
    //赠送保险（0 否 1是）
    private Integer enabledInsurance;
    //保险方案
    private String insuranceScheme;
    //方案id
    private Integer schemeId;
    //微信二维码
    private String wxCode;
    //联系电话
    private String phone;
    //宠物照片详情
    private String petImgUrl;
    //宠物昵称（领养必填）
    private String nickName;
    //活体类型（0 活体 1 领养）
    private Integer type;
    //点赞个数
    private Integer supportCount;
    //默认上架(0 下架 -1 删除 1 下架)
    private Integer status = 1;
    private Date updateTime;

    private String userName;

    //用户是否点赞 1 是 0 否
    private Integer enabledSupport = 0;


}
