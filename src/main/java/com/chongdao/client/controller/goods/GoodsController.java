package com.chongdao.client.controller.goods;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Brand;
import com.chongdao.client.entitys.GoodsType;
import com.chongdao.client.repository.BrandRepository;
import com.chongdao.client.repository.GoodsTypeRepository;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.BrandGoodsTypeVO;
import com.chongdao.client.vo.GoodsDetailVo;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/goods/")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private GoodsTypeRepository goodsTypeRepository;


    /**
     * 商品列表展示
     * @param keyword 搜索关键词
     * @param pageNum
     * @param pageSize
     * @param brandId
     * @param goodsTypeId
     * @param scopeId 适用期(仅猫/狗粮)
     * @param petCategoryId 适用类型(仅狗粮)
     * @param orderBy 排序方式(价格、销量、好评等)
     * @return
     */
    @GetMapping("list")
    public ResultResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "brandId", required = false) Integer brandId,
                                         @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId,
                                         @RequestParam(value = "scopeId", required = false) Integer scopeId,
                                         @RequestParam(value = "petCategoryId", required = false) Integer petCategoryId,
                                         @RequestParam(value = "orderBy",defaultValue = "arrangement",required = false) String orderBy){

        return goodsService.getGoodsByKeyword(keyword,pageNum,pageSize,brandId,goodsTypeId,scopeId,petCategoryId,orderBy);
    }


    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @GetMapping("getGoodsDetail/{goodsId}")
    public ResultResponse<GoodsDetailVo>  getGoodsDetail(@PathVariable Integer goodsId,@RequestParam(required = false) Integer userId){
        return goodsService.getGoodsDetail(goodsId,userId);
    }


    /**
     * 商品分类和品牌
     * @return
     */
    @GetMapping("getCategory")
    public ResultResponse getBrand(){
        //获取所有类别
        List<GoodsType> goodsTypeList = goodsTypeRepository.findByStatus(1);
        List<BrandGoodsTypeVO> brandGoodsTypeVOList = Lists.newArrayList();
        goodsTypeList.stream().forEach(goodsType -> {
            //根据类别获取所属品牌
            List<Brand> brandList = brandRepository.findByGoodsTypeId(goodsType.getId()).orElse(null);
            BrandGoodsTypeVO  brandGoodsTypeVO = new BrandGoodsTypeVO();
            //填充信息
            brandGoodsTypeVO.setGoodsTypeId(goodsType.getId());
            brandGoodsTypeVO.setGoodsTypeName(goodsType.getName());
            brandGoodsTypeVO.setBrandList(brandList);
            brandGoodsTypeVO.setCategoryId(goodsType.getCategoryId());
            brandGoodsTypeVOList.add(brandGoodsTypeVO);
        });
        return ResultResponse.createBySuccess(brandGoodsTypeVOList);
    }

    /**
     * 获取品牌
     * @return
     */
    @GetMapping("getBrandList/{goodsTypeId}")
    public ResultResponse<List<Brand>> getBrandList(@PathVariable Integer goodsTypeId){
        return goodsService.getBrandList(goodsTypeId);
    }


    /**
     * 获取适用期和使用类型
     * @param goodsTypeId
     * @param brandId
     * @return
     */
    @GetMapping("getScopeType/{goodsTypeId}/{brandId}")
    public ResultResponse getScopeType(@PathVariable Integer goodsTypeId, @PathVariable Integer brandId){
        return goodsService.getScopeType(goodsTypeId, brandId);
    }

    /**
     * 商品收藏/取消
     * @param goodsId
     * @param token
     * @return
     */
    @PostMapping
    public ResultResponse concernGoods(@RequestParam Integer goodsId,String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return goodsService.concernGoods(tokenVo.getUserId(), goodsId);
    }

    /**
     * 查看收藏商品列表
     * @param token
     * @return
     */
    @GetMapping
    public ResultResponse queryConcernShopList(String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return goodsService.queryConcernGoodsList(tokenVo.getUserId());
    }

}
