package com.chongdao.client.entitys;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 运输险中间记录实体, 用户下单后现在这生成记录, 然后配送员接单时再查询这个实体生成保单
 * @Author onlineS
 * @Date 2019/10/10
 * @Version 1.0
 **/
public class ZcgInsuraneMiddleRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer orderId;//关联的订单ID
    private Integer isInsuranced;//是否购买保险
    private Date createTime;
}
