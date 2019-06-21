package com.chongdao.client.controller.manage.coupon;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.service.coupon.CouponInfoService;
import com.chongdao.client.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fenglong
 * @date 2019-06-18 16:43
 */

@RestController
@RequestMapping("/api/manage/coupon/")
@CrossOrigin
public class CouponManagerController {

    @Autowired
    private CouponInfoService couponInfoService;


    /**
     * 添加优惠券
     * @param couponInfo
     * @return
     */
    @PostMapping("add")
    public ResultResponse add(String token,CouponInfo couponInfo){
        LoginUserUtil.resultTokenVo(token);
        return couponInfoService.add(couponInfo);
    }


    /**
     * 获取分类
     * @param categoryId 3限商品 4限服务
     * @return
     */
    @GetMapping("getCategory/{categoryId}")
    public ResultResponse getCategory(@PathVariable Integer categoryId){
        return couponInfoService.getCategory(categoryId);
    }
}
