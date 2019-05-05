package com.chongdao.client.controller.coupon;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.CouponVO;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param type 优惠券类型(0：商品 1: 服务)
     * @return
     */
    @GetMapping("getCouponListByShopId")
    public ResultResponse<List<CouponVO>> getCouponListByShopId(Integer shopId, Integer type,String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return couponService.getCouponListByShopIdAndType(tokenVo.getUserId(),shopId, type);
    }


    /**
     * 领取优惠券
     * @param userId
     * @param shopId
     * @param couponId
     * @return
     */
    @GetMapping("receive_coupon")
    public ResultResponse receiveCoupon(@RequestParam("userId")Integer userId,
                                        @RequestParam("shopId") Integer shopId,
                                        @RequestParam("couponId") Integer couponId,
                                        @RequestParam("token") String token){
        LoginUserUtil.resultTokenVo(token);
        return couponService.receiveCoupon(userId, shopId, couponId);
    }


    /**
     * 查询已领取优惠券(商品)
     * @param userId
     * @param shopId
     * @param token
     * @return
     */
    @GetMapping("receive_coupon_complete")
    public ResultResponse receiveCouponComplete(@RequestParam("userId")Integer userId,
                                                @RequestParam("shopId") Integer shopId,
                                                @RequestParam("token") String token){

        LoginUserUtil.resultTokenVo(token);
        return couponService.receiveCouponComplete(userId, shopId);
    }


    /**
     * 查询已领取的配送优惠券
     * @param userId
     * @param token
     * @param param 0双程 1 单程
     * @return
     */
    @GetMapping("card_service_list")
    public ResultResponse getCardServiceList(@RequestParam("userId")Integer userId,
                                                @RequestParam("param") String param,
                                                @RequestParam("token") String token){

        LoginUserUtil.resultTokenVo(token);
        return couponService.getCardServiceList(userId, param);
    }



}
