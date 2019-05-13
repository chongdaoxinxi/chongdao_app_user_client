package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shopGoodManage/")
public class ShopGoodManageController {

    /**
     * 获取商品类别
     * @param token
     * @return
     */
    public ResultResponse getGoodCategoryList(String token) {
        return null;
    }

    /**
     * 获取商品列表
     * @param token
     * @param categoryId
     * @param goodName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultResponse getGoodList(String token, Integer categoryId, Integer goodName, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 商品下架
     * @param goodId
     * @return
     */
    public ResultResponse offShelveGood(Integer goodId) {
        return null;
    }

    /**
     * 删除商品
     * @param goodId
     * @return
     */
    public ResultResponse removeGood(Integer goodId) {
        return null;
    }

    /**
     * 打折商品
     * @param token
     * @param categoryId
     * @return
     */
    public ResultResponse discountGood(String token, Integer categoryId) {
        return null;
    }
}
