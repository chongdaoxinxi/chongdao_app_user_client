package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Brand;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.GoodsListVO;
import com.chongdao.client.vo.PetCategoryAndScopeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private GoodsService goodsService;

    /**
     * 获取商品类别
     * @param token
     * @return
     */
    @GetMapping("/get_good_category_list")
    public ResultResponse getGoodCategoryList(String token, Integer shopId) {
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
    @GetMapping("get_good_list")
    public ResultResponse getGoodList(String token,
                                      @RequestParam(value = "shopId") Integer shopId,
                                      @RequestParam(required = false) Integer goodsTypeId,
                                      @RequestParam(required = false) Integer goodName,
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
    @GetMapping("update_goods_status")
    public ResultResponse offShelveGood(String token,Integer goodId,Integer status) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.updateGoodsStatus(goodId, status);
    }


    /**
     * 打折商品
     * @param token
     * @param goodsTypeId
     * @return
     */
    @GetMapping("goods_discount")
    public ResultResponse discountGood(String token,Integer shopId, Integer goodsTypeId, Double discount) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.discountGood(shopId,goodsTypeId,discount);
    }


    /**
     * 获取所有商品分类（官方定义）
     * @param token
     * @param shopId
     * @return
     */
    @GetMapping("category_list")
    public ResultResponse categoryList(String token,Integer shopId){
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
    @GetMapping("add_goods")
    public ResultResponse addOrEditGoods(String token, Integer shopId, GoodsListVO goodsListVO){
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
    @GetMapping("select_goods_by_id")
    public ResultResponse selectGoodsById(String token, Integer shopId, Integer goodsId){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.selectGoodsById(shopId,goodsId);
    }

    /**
     * 提高系数
     * @param goodsTypeId (0代表全部)
     * @return
     */
    @GetMapping("improve_ratio")
    public ResultResponse improveRatio(String token,Double ratio,Integer goodsTypeId,Integer shopId){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.improveRatio(ratio,goodsTypeId,shopId);
    }

    /**
     * 一键恢复
     * @param token
     * @param shopId
     * @return
     */
    @GetMapping("recover_all")
    public ResultResponse recoverAll(String token,Integer shopId){
        LoginUserUtil.resultTokenVo(token);
        return goodsService.recoverAll(shopId);
    }

    /**
     * 获取品牌
     * @return
     */
    @GetMapping("get_brand_list")
    public ResultResponse<List<Brand>> getBrandList(){
        return goodsService.getBrandList();
    }

    /**
     * 获取宠物试用期以及使用范围分类
     * @param petCategoryId
     * @return
     */
    @GetMapping("get_pet_category")
    public ResultResponse<List<PetCategoryAndScopeVO>> getPetCategory(Integer categoryId,Integer petCategoryId){
        return goodsService.getPetCategory(categoryId,petCategoryId);
    }

    /**
     * 获取洗澡服务内容
     * @return
     */
    @GetMapping("get_bathing_service")
    public ResultResponse getBathingService(){
        return goodsService.getBathingService();
    }




}
