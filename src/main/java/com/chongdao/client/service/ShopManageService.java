package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.vo.ShopManageVO;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
public interface ShopManageService {
    ResultResponse shopLogin(String name, String password);

    ResultResponse shopLogout();

    ResultResponse<ShopManageVO> getShopInfo(Integer shopId);

    ResultResponse saveShopInfo(Shop s);
}
