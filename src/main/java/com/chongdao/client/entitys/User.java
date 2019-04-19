package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @Author onlineS
 * @Description 用户表
 * @Date 9:26 2019/4/19
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 用户名 */
    private String name;
    /** 密码 */
    private String password;
    /** 手机号码 */
    private String phone;
    /** 头像 */
    private String icon;
    /** 用户标识（微信） */
    private String openId;
    /** 用户余额（充值） */
    private BigDecimal money;
    /** 集分 */
    private Integer points;
    private Integer status; //待明确
    /** 用户类型（1:app用户,2:微信用户） */
    private Integer type;
    /** 用户创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
    /** 最后一次登录时间（token鉴别有效期使用） */
    private Date lastLoginTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", icon='" + icon + '\'' +
                ", openId='" + openId + '\'' +
                ", money=" + money +
                ", points=" + points +
                ", status=" + status +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
