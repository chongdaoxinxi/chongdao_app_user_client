package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/17
 * @Version 1.0
 **/
@Getter
@Setter
@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal min;
    private BigDecimal max;
    private String label;
    private Byte type;//1:商品;2:服务(洗澡/美容/spa/寄样)
    private String categoryIdList;//可以使用的服务类分类id
}
