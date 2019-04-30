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
    private int id;
    @Column(name = "user_id")
    private int userId;//主人id
    private String name;//宠物昵称
    private String icon;//宠物头像
    private Integer sex;//宠物性别, 1:男, 2:绝育男, 3:女, 4:绝育女
    @Column(name = "type_id")
    private int typeId;//宠物种类id
    private String breed;//宠物品种
    private BigDecimal weight;//宠物体重
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_date")
    private Date birthDate;//宠物出生日期
    @Column(name = "is_has_dog_certificate")
    private Integer isHasDogCertificate;//是否有狗证 1:有, -1:没有
    @Column(name = "is_has_vaccine_certificate")
    private Integer isHasVaccineCertificate;//是否有疫苗证 1:有, -1:没有
    private Integer status;//-1:删除, 0:不激活状态, 1:激活状态;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    //冗余字段
    @Column(name = "type_name")
    private String typeName;//宠物种类名称
    @Column(name = "type_icon")
    private String typeIcon;//宠物种类标志

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
