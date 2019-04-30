package com.chongdao.client.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVo {

    private Integer orderNo;

    private Integer userId;

    private Integer ShopId;

    private BigDecimal goodsPrice;

    private BigDecimal servicePrice;

    private BigDecimal totalDiscount;

    private BigDecimal payment;

    private Byte follow;

    private Integer serviceType;

    private Integer cardId;

    private String remark;

    private Date createTime;

    private Date deliverTime;

    private Date paymentTime;

    private Byte paymentType;

    private Date shopReceiveTime;

    private Integer expressId;

    private Date expressReceiveTime;

    private Date expressFinishTime;

    private Integer orderStatus;

    private BigDecimal reward;



    //订单明细
    private List<OrderGoodsVo> orderGoodsVoList;







}
