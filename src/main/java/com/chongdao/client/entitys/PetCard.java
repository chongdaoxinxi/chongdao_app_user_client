package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 宠物资料卡片
 * @Author onlineS
 * @Date 2019/4/25
 * @Version 1.0
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pet_card")
public class PetCard implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;//主人id
    private String name;//宠物昵称
    private String icon;//宠物头像
    private Integer sex;//宠物性别, 1:男, 2:绝育男, 3:女, 4:绝育女
    private Integer typeId;//宠物种类id
    private Integer breedId;//宠物品种ID
    private String breed;//宠物品种
    private BigDecimal weight;//宠物体重
    private Date birthDate;//宠物出生日期
    private Integer age;//年龄
    private Integer isHasDogCertificate;//是否有狗证 1:有, -1:没有
    private Integer isHasVaccineCertificate;//是否有疫苗证 1:有, -1:没有
    private Integer status;//-1:删除, 0:不激活状态, 1:激活状态;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    //冗余字段
    private String typeName;//宠物种类名称
    private String typeIcon;//宠物种类标志

    //匹配符合该宠物的服务Id
    @Transient
    private Integer goodsId;
    //匹配符合该宠物的服务
    @Transient
    private String goodsName;
    //多少人付款
    @Transient
    private Integer paymentNumber;
    @Transient
    private BigDecimal goodsPrice;
    @Transient
    private Integer checked;


    @Override
    public String toString() {
        return "PetCard{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sex=" + sex +
                ", typeId=" + typeId +
                ", breed='" + breed + '\'' +
                ", weight=" + weight +
                ", birthDate=" + birthDate +
                ", isHasDogCertificate=" + isHasDogCertificate +
                ", isHasVaccineCertificate=" + isHasVaccineCertificate +
                ", status=" + status +
                ", createTime=" + createTime +
                ", typeName='" + typeName + '\'' +
                ", typeIcon='" + typeIcon + '\'' +
                '}';
    }
}
