package com.chongdao.client.mapper;

import com.chongdao.client.entitys.ShopBill;
import com.chongdao.client.vo.ShopBillVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ShopBillMapper {
    List<ShopBillVO> getShopBillByShopId(@Param("shopId") Integer shopId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<ShopBill> getShopBillByAreaCode(@Param("areaCode") String areaCode, @Param("shopName") String shopName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 获取订单消费/追加订单的入账流水记录
     * @param orderId
     * @return
     */
    List<ShopBill> getInBillByOrderId(@Param("orderId") Integer orderId);
}
