package com.chongdao.client.mapper;

import com.chongdao.client.entitys.ShopBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ShopBillMapper {
    List<ShopBill> getShopBillByShopId(@Param("shopId") Integer shopId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<ShopBill> getShopBillByAreaCode(@Param("areaCode") String areaCode, @Param("shopName") String shopName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
