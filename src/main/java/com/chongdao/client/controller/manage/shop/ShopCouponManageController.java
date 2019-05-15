package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_coupon_manage/")
public class ShopCouponManageController {

    @Autowired
    private CouponService couponService;


    /**
     * 添加满减优惠券
     * @param couponVO
     * @return
     */
    @GetMapping("/addCoupon")
    public ResultResponse<CouponVO> addCoupon(String token, CouponVO couponVO, Integer type, Integer shopId){
        LoginUserUtil.resultTokenVo(token);
        couponService.save(shopId, couponVO, type);
        return ResultResponse.createBySuccess();
    }


    /**
     * 查询优惠券(包含店铺满减)
     * @param shopId
     * @param type 0 店铺满减 2 优惠券
     * @return
     */
    @GetMapping("/findCouponByShopId")
    public ResultResponse<List<CouponVO>> findCouponByShopId(Integer shopId, Integer type){
        return  couponService.findByShopId(shopId, type);
    }


    /**
     * 优惠券上架、下架、删除（1，0，-1）
     * @param couponId
     * @return
     */
    @GetMapping("/updateCouponStatusById")
    public ResultResponse<Integer> updateCouponStatusById(String token, Integer couponId, Integer status){
        LoginUserUtil.resultTokenVo(token);
        couponService.updateCouponStatusById(couponId, status);
        return ResultResponse.createBySuccess();
    }

}
