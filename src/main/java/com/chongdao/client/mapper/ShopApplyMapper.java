package com.chongdao.client.mapper;

import com.chongdao.client.entitys.ShopApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ShopApplyMapper {
    List<ShopApply> getShopApplyListPc(@Param("shopId") Integer shopId, @Param("areaCode") String areaCode, @Param("shopName") String shopName, @Param("status") Integer status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
