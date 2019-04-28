package com.chongdao.client.controller.coupon;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coupon/")
public class CouponController {

    @Autowired
    private CouponService couponService;
    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(1：商品 2: 服务)
     * @return
     */
    @GetMapping("getCouponListByShopId")
    public ResultResponse<List<CouponVO>> getCouponListByShopId(Integer shopId, Integer type){
        return couponService.getCouponListByShopIdAndType(shopId, type);
    }
}
