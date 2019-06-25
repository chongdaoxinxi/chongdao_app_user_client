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
    public ResultResponse add(@RequestBody CouponInfo couponInfo){
        LoginUserUtil.resultTokenVo(couponInfo.getToken());
        return couponInfoService.add(couponInfo);
    }

    /**
     * 根据商品id更新相应状态
     * @param cpnId
     * @param state 状态-1 已删除 0待发布 1已发布 2已下架
     * @param token
     * @return
     */
    @PutMapping("updateState/{cpnId}/{state}")
    public ResultResponse updateState(@PathVariable Integer cpnId,@PathVariable Integer state,String token){
        LoginUserUtil.resultTokenVo(token);
        return couponInfoService.updateState(cpnId,state);
    }

    /**
     * 查询商家所有优惠券
     * @param shopId
     * @param token
     * @return
     */
    @GetMapping("list/{shopId}")
    public ResultResponse list(@PathVariable Integer shopId,String token){
        LoginUserUtil.resultTokenVo(token);
        return couponInfoService.list(shopId);
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
