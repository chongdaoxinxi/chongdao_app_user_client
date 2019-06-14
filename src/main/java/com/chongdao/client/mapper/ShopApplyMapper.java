package com.chongdao.client.mapper;

import com.chongdao.client.entitys.ShopApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopApplyMapper {
    List<ShopApply> getShopAppliesByShopName(String shopName);
}
