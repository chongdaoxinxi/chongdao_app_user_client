package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Brand;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.service.UnitService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.GoodsListVO;
import com.chongdao.client.vo.PetCategoryAndScopeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_good_manage/")

public class ShopGoodManageController {
    @Autowired
    private UnitService unitService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 获取商品类别
     * @param token
     * @return
     */
    @GetMapping("getGoodCategoryList/{shopId}")
    public ResultResponse getGoodCategoryList(@PathVariable Integer shopId,String token) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.getGoodCategoryList(shopId);
    }

    /**
     * 获取商品列表
     * @param token
     * @param goodsTypeId
     * @param goodName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getGoodList")
    public ResultResponse getGoodList( @RequestParam(value = "shopId") Integer shopId,
                                       @RequestParam(required = false) Integer goodsTypeId,
                                       String token,
                                      @RequestParam(required = false) String goodName,
                                      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.getGoodList(shopId,goodsTypeId,goodName,pageNum,pageSize);
    }

    /**
     * 商品下架
     * @param goodId
     * @param status 1:上架,0下架，-1删除
     * @return
     */
    @GetMapping("updateGoodsStatus/{goodId}")
    public ResultResponse offShelveGood(@PathVariable Integer goodId,String token,Integer status) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.updateGoodsStatus(goodId, status);
    }


    /**
     * 打折商品
     * @param token
     * @param goodsTypeId
     * @return
     */
    @GetMapping("goodsDiscount")
    public ResultResponse discountGood(@PathVariable Integer shopId, @PathVariable Integer goodsTypeId,String token, Double discount) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.discountGood(shopId,goodsTypeId,discount);
    }


    /**
     * 获取所有商品分类（官方定义）
     * @param token
     * @param shopId
     * @return
     */
    @GetMapping("categoryList/{shopId}")
    public ResultResponse categoryList(@PathVariable Integer shopId,String token){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.categoryList(shopId);
    }


    /**
     * 添加或编辑商品
     * @param token
     * @param shopId
     * @param goodsListVO
     * @return
     */
    @PostMapping("addGoods/{shopId}")
    public ResultResponse addOrEditGoods(@PathVariable Integer shopId,String token,  GoodsListVO goodsListVO){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.saveOrEditGoods(shopId,goodsListVO);
    }


    /**
     * 根据商品id查询
     * @param token
     * @param shopId
     * @param goodsId
     * @return
     */
    @GetMapping("selectGoodsById/{shopId}/{goodsId}")
    public ResultResponse selectGoodsById( @PathVariable Integer shopId, @PathVariable Integer goodsId,String token){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.selectGoodsById(shopId,goodsId);
    }

    /**
     * 提高系数
     * @param goodsTypeId (0代表全部)
     * @return
     */
    @GetMapping("improveRatio/{goodsTypeId}/{shopId}")
    public ResultResponse improveRatio(@PathVariable Integer goodsTypeId,@PathVariable Integer shopId,String token,Double ratio){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.improveRatio(ratio,goodsTypeId,shopId);
    }

    /**
     * 一键恢复
     * @param token
     * @param shopId
     * @return
     */
    @PutMapping("recoverAll/{shopId}")
    public ResultResponse recoverAll(@PathVariable Integer shopId,String token){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.recoverAll(shopId);
    }

    /**
     * 启用/禁用/删除 商品类别
     * @param token
     * @param goodTypeId
     * @param status
     * @return
     */
    @PutMapping("updateGoodTypeStatus/{goodTypeId}")
    public ResultResponse updateGoodTypeStatus(@PathVariable Integer goodTypeId,String token, Integer status) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.updateGoodTypeStatus(goodTypeId, status);
    }

    /**
     * 获取品牌
     * @return
     */
    @GetMapping("getBrandList")
    public ResultResponse<List<Brand>> getBrandList(){
        return goodsService.getBrandList();
    }

    /**
     * 获取宠物试用期以及使用范围分类
     * @param petCategoryId
     * @return
     */
    @GetMapping("getPetCategory/{categoryId}/{petCategoryId}")
    public ResultResponse<List<PetCategoryAndScopeVO>> getPetCategory(@PathVariable Integer categoryId, @PathVariable Integer petCategoryId){
        return goodsService.getPetCategory(categoryId,petCategoryId);
    }

    /**
     * 获取洗澡服务内容
     * @return
     */
    @GetMapping("getBathingService")
    public ResultResponse getBathingService(){
        return goodsService.getBathingService();
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
