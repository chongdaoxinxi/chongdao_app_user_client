package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
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
 * @Author onlineS
 * @Description 商家表
 * @Date 9:07 2019/4/19
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shop extends PageParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String shopName;
    private String phone;
    private String accountName;//登录名
    private String password;
    private Integer areaId;
    private String areaCode;
    private Double lat;
    private Double lng;
    private String logo;
    private BigDecimal money;
    private Integer type;
    private String address;
    private Double grade;//评分
    private String wxNo;
    private String zfbNo;
    private String bankNo;
    /**
     * 商家二维码
     */
    private String qrCodeUrl;
    private String des;//店铺描述
    private String showImg;//店铺展示图片
    /**
     * -1:停业;0:休息;1:营业中
     */
    private Integer status;
    /**
     * 提现服务费比例
     */
    private Integer servicePriceRatio;
    private String stopNote;//停止营业公告

    private Byte isHot;//是否热门商家
    private Byte isAutoAccept;//是否自动接单
    private Byte isStop;//是否停止营业


    /**
     * 参与公益：0，未参与，1参与
     */
    private Byte isJoinCommonWeal;

    /**
     * 营业时间
     */
    private String startBusinessHours;

    /**
     * 结束营业时间
     */
    private String endBusinessHours;

    private Date createTime;

    private Date updateTime;

    /**
     * 最后一次登录时间（token鉴别有效期使用）
     */
    private Date lastLoginTime;

    public Shop(Integer id, String shopName, String phone, String accountName, String password, Integer areaId, String areaCode, Double lat, Double lng, String logo, BigDecimal money, Integer type, String address, Double grade, String wxNo, String zfbNo, String bankNo, String qrCodeUrl, String des, String showImg, Integer status, Integer servicePriceRatio, String stopNote, Byte isHot, Byte isAutoAccept, Byte isStop, Byte isJoinCommonWeal, String startBusinessHours, String endBusinessHours, Date createTime, Date updateTime, Date lastLoginTime) {
        this.id = id;
        this.shopName = shopName;
        this.phone = phone;
        this.accountName = accountName;
        this.password = password;
        this.areaId = areaId;
        this.areaCode = areaCode;
        this.lat = lat;
        this.lng = lng;
        this.logo = logo;
        this.money = money;
        this.type = type;
        this.address = address;
        this.grade = grade;
        this.wxNo = wxNo;
        this.zfbNo = zfbNo;
        this.bankNo = bankNo;
        this.qrCodeUrl = qrCodeUrl;
        this.des = des;
        this.showImg = showImg;
        this.status = status;
        this.servicePriceRatio = servicePriceRatio;
        this.stopNote = stopNote;
        this.isHot = isHot;
        this.isAutoAccept = isAutoAccept;
        this.isStop = isStop;
        this.isJoinCommonWeal = isJoinCommonWeal;
        this.startBusinessHours = startBusinessHours;
        this.endBusinessHours = endBusinessHours;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.lastLoginTime = lastLoginTime;
    }
}