package com.chongdao.client.controller.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.CategoryService;
import com.chongdao.client.service.GoodsTypeService;
import com.chongdao.client.service.ModuleService;
import com.chongdao.client.service.UnitService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 商家PC端
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_pc/")
public class ShopPcController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private UnitService unitService;

    @GetMapping("getModuleData")
    public ResultResponse getModuleData(String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if(role != null && role.equals("SHOP_PC")) {
            return moduleService.getModuleData();
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    @GetMapping("getCategoryData")
    public ResultResponse getCategoryData(String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if(role != null && role.equals("SHOP_PC")) {
            return categoryService.getCategoryData();
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    @RequestMapping(value="addGoodsType", method = RequestMethod.POST)
    @ResponseBody
    public ResultResponse addGoodsType(GoodsType goodsType) {
        return goodsTypeService.addGoodsType(goodsType);
//        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
//        String role = tokenVo.getRole();
//        if(role != null && role.equals("SHOP_PC")) {
//            return goodsTypeService.addGoodsType(goodsType);
//        } else {
//            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
//        }
    }

    /**
     * 获取规格单位
     * @param moduleId
     * @param categoryId
     * @return
     */
    @GetMapping("getUnitList")
    public ResultResponse getUnitList(Integer moduleId, Integer categoryId) {
        return unitService.getUnitList(moduleId, categoryId);
    }
}
