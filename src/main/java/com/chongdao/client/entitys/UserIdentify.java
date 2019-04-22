package com.chongdao.client.entitys;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户扫描领养机构二维码表
 */
@Data
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_identify")
public class UserIdentify implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**  用户名 **/
    private String userName;
    private Integer userId;
    /** 用户电话 */
    private String userPhone;
    /** 所属店铺 */
    private Integer adoptionShopId;
    /** 领养机构 */
    private String adoptionAgency;
    /** 领养类别 */
    private String adoptionCategory;
    /** 所属区域 */
    private String region;
    /** 用户标识 */
    private String userIdentify;
    private Integer status;
    private Date createTime;

    @Override
    public String toString() {
        return "UserIdentify{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", userPhone='" + userPhone + '\'' +
                ", adoptionShopId=" + adoptionShopId +
                ", adoptionAgency='" + adoptionAgency + '\'' +
                ", adoptionCategory='" + adoptionCategory + '\'' +
                ", region='" + region + '\'' +
                ", userIdentify='" + userIdentify + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
