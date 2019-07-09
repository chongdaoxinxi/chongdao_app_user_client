package com.chongdao.client.entitys;

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
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/9
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserShopVisit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private Integer shopId;
    private Integer isOld;
    private Integer source;
    private Date createTime;
}
