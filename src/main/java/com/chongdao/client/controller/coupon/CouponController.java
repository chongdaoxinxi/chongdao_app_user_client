package com.chongdao.client.controller.coupon;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coupon/")
public class CouponController {

    @Autowired
    private CouponService couponService;


    /**
     * 订单优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(0：商品以及服务优惠券 1: 配送券)
     * @param serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @param categoryId 检验商品类型（主要是针对服务和商品的优惠券使用场景）
     * @return
     */
    @PostMapping("getCouponListByShopId/{shopId}/{type}")
    public ResultResponse getCouponListByShopId(@PathVariable String shopId,
                                                @PathVariable Integer type,
                                                String categoryId,
                                                BigDecimal totalPrice,
                                                Integer serviceType,
                                                String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return couponService.getCouponListByShopIdAndType(tokenVo.getUserId(),shopId,categoryId,totalPrice,type,serviceType);
    }


    /**
     * 领取优惠券
     * @return
     */
    @PostMapping("receiveCoupon")
    public ResultResponse receiveCoupon(@RequestBody CouponInfo couponInfo){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(couponInfo.getToken());
        return couponService.receiveCoupon(tokenVo.getUserId(), couponInfo);
    }

    /**
     * 卡包
     * @param userId
     * @param token
     * @return
     */
    @GetMapping("couponList/{userId}")
    public ResultResponse couponList(@PathVariable Integer userId,@RequestParam String token){
        LoginUserUtil.resultTokenVo(token);
        return couponService.couponList(userId);

    }


}
