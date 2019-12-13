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
 * @Description 推送相关用户标识
 * @Author onlineS
 * @Date 2019/12/11
 * @Version 1.0
 **/
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private String regId;
    private String alias;
    private String userAccount;
    private Date createTime;
    private Date updateTime;
}
