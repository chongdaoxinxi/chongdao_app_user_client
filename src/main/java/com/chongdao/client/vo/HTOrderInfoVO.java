package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-17 14:35
 */
@Getter
@Setter
public class HTOrderInfoVO {

    //活体订单号
    private String htOrderNo;

    //买家id
    @NotEmpty(message = "buyerUserId不能为空")
    private Integer buyerUserId;

    //卖家id
    @NotEmpty(message = "sellUserId不能为空")
    private Integer sellUserId;

    //订单类型 1：活体 2领养
    @NotEmpty(message = "orderType不能为空")
    private Integer orderType;

    //服务类型：1 单程 2到店自取
    @NotEmpty(message = "serviceType不能为空")
    private Integer serviceType;

    //接地址id
    //@NotEmpty(message = "receiveId不能为空")
    private Integer receiveId;

    //接宠时间
    //@NotEmpty(message = "receiveTime不能为空")
    private Date receiveTime;

    //活体id
    @NotEmpty(message = "livingId不能为空")
    private Integer livingId;

    //配送券id
    private Integer serviceId;

    //配送费
    @NotEmpty(message = "servicePrice不能为空")
    private BigDecimal servicePrice;

    //参考orderStatusEnum类
    private Integer orderStatus;

    //实际付款
    @NotEmpty(message = "payment不能为空")
    private BigDecimal payment;

    //订单总价
    private BigDecimal totalPrice;

    //折扣价
    private BigDecimal discountPrice;

    //支付方式 1 支付宝 2微信
    @NotEmpty(message = "payType不能为空")
    private Integer payType;

    //备注
    private String remark;

    @NotEmpty(message = "token不能为空")
    private String token;

    private String sellUserName;

    private String sellHeadIcon;

    //创建订单类型 1 预下单 2提交订单
    private Integer createOrderType;
}
