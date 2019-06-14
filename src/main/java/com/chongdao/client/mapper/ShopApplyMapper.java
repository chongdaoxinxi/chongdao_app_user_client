package com.chongdao.client.mapper;

import com.chongdao.client.entitys.ShopApply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopApplyMapper {
    ShopApply getShopAppliesByShopName(String shopName);
}
