package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author onlineS
 * @Description 用户账户
 * @Date 9:27 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="user_account")
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private BigDecimal money;
    private Integer type;
    private Integer status;
    private String areaCode;

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", money=" + money +
                ", type=" + type +
                ", status=" + status +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}