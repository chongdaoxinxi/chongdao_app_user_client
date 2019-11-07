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
 * @Description 订单流转操作日志
 * @Author onlineS
 * @Date 2019/11/7
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderOperateLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer orderId;
    private String orderNo;
    private Integer oldStatus;//旧状态
    private Integer targetStatus;//流转目标状态
    private String note;//注释
    private Date createTime;
}
