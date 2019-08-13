package com.chongdao.client.dto;

import com.chongdao.client.vo.OrderGoodsVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fenglong
 * @date 2019-08-12 13:41
 */
@Getter
@Setter
public class OrderGoodsDTO {

    private BigDecimal cartTotalPrice;
    private List<OrderGoodsVo> orderGoodsVoList;
}
