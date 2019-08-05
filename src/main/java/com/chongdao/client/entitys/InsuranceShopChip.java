package com.chongdao.client.entitys;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 保险宠物芯片(店铺)
 * @Author onlineS
 * @Date 2019/7/30
 * @Version 1.0
 **/
public class InsuranceShopChip implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer shopId;
    private String core;
    private Integer status;//1:可用;0:被选择;-1:已使用;
    private Date createTime;
}
