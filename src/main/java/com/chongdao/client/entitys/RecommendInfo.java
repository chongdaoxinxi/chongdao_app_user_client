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
 * @Description 推广码信息
 * @Author onlineS
 * @Date 2019/7/30
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
public class RecommendInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String recommendCode;//推广码
    private Integer userId;
    private Integer expressId;
    private Integer shopId;
    private Integer type;//1:用户推广;2:配送员推广;3:商家推广;
    private Date createTime;
}
