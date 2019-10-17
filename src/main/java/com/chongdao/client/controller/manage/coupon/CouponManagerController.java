package com.chongdao.client.controller.manage.coupon;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.CpnParam;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.repository.coupon.CpnParamRepository;
import com.chongdao.client.service.coupon.CouponInfoService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.CpnParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private CpnParamRepository cpnParamRepository;

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
    @PostMapping("updateState/{cpnId}/{state}")
    public ResultResponse updateState(@PathVariable Integer cpnId,@PathVariable Integer state,@RequestParam String token){
        LoginUserUtil.resultTokenVo(token);
        return couponInfoService.updateState(cpnId,state);
    }

    /**
     * 查询商家所有优惠券
     * @param shopId
     * @param token
     * @return
     */
    @GetMapping("list/{shopId}/{state}/{cpnType}")
    public ResultResponse list(@PathVariable Integer shopId,
                               @PathVariable Integer state,
                               @PathVariable Integer cpnType,
                               @RequestParam String token,
                               Integer goodsTypeId){
        LoginUserUtil.resultTokenVo(token);
        return couponInfoService.list(shopId,state,cpnType,goodsTypeId);
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

    /**
     * 获取优惠券参数列表
     * @param token
     * @param paramType  区分商家可使用的类型：0 商家 1管理员
     * @return
     */
    @GetMapping("getCpnParam")
    public ResultResponse getCpnParam(@RequestParam String token,@RequestParam Integer paramType){
        LoginUserUtil.resultTokenVo(token);
        //适用范围
        List<CpnParam> cpnScopeList = null;
        //优惠类型
        List<CpnParam> cpnTypeList = null;
        if (1 == paramType) {
            cpnScopeList = cpnParamRepository.findByScopeTypeNotNull();
            cpnTypeList = cpnParamRepository.findByCpnTypeNotNull();
        }else {
            cpnScopeList = cpnParamRepository.findByScopeTypeNotNullAndParamType(paramType);
            cpnTypeList = cpnParamRepository.findByCpnTypeNotNullAndParamType(paramType);
        }
        CpnParamVo cpnParamVo = new CpnParamVo();
        cpnParamVo.setCpnScopeList(cpnScopeList);
        cpnParamVo.setCpnTypeList(cpnTypeList);
        return ResultResponse.createBySuccess(cpnParamVo);
    }
}
