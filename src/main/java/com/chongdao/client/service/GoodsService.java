package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Brand;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.vo.GoodsDetailVo;
import com.chongdao.client.vo.GoodsListVO;
import com.chongdao.client.vo.PetCategoryAndScopeVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService {


    /**
     * 商品列表展示
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @return
     */
    ResultResponse<PageInfo> getGoodsByKeyword(String keyword, int pageNum, int pageSize,Integer brandId,Integer goodsTypeId,Integer scopeId,Integer petCategoryId,  String areaCode, String orderBy);

    /**
     * 商品详情
     * @param goodsId
     * @return
     */
    ResultResponse<GoodsDetailVo> getGoodsDetail(Integer goodsId,Integer userId);




    //-------------------------------------------------------商户端接口-------------------------------------------------------------------------------------//

    /**
     * 获取商品类别
     * @return
     */
    ResultResponse getGoodCategoryList();

    /**
     * 获取商品列表
     * @param goodsTypeId
     * @param goodName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getGoodList(Integer shopId,Integer goodsTypeId, String goodName, int pageNum, int pageSize);


    /**
     * 商品下架
     * @param goodId
     * @param status 1:上架,0下架，-1删除
     * @return
     */
    ResultResponse updateGoodsStatus(Integer goodId, Integer status);


    /**
     * 商品打折
     * @param reDiscount 第二件折扣
     * @param goodsTypeId
     * @param discount
     * @return
     */
    ResultResponse discountGood(Integer shopId,Integer goodsTypeId, Double discount, Double reDiscount);

    /**
     * 获取商品分类
     * @param shopId
     * @return
     */
    ResultResponse categoryList(Integer shopId);

    /**
     * 增加或编辑商品
     * @param shopId
     * @param goodsListVO
     * @return
     */
    ResultResponse saveOrEditGoods(Integer shopId, GoodsListVO goodsListVO);

    ResultResponse saveGood(Good good);

    /**
     * 根据商品id查询
     * @param shopId
     * @param goodsId
     * @return
     */
    ResultResponse selectGoodsById(Integer shopId, Integer goodsId);

    /**
     * 提高系数
     * @param goodsTypeId
     * @return
     */
    ResultResponse improveRatio(Double ratio,Integer goodsTypeId,Integer shopId);

    /**
     * 一键恢复
     * @param shopId
     * @return
     */
    ResultResponse recoverAll(Integer shopId);

    ResultResponse updateGoodTypeStatus(Integer goodTypeId, Integer status);

    /**
     * 获取品牌
     * @return
     */
    ResultResponse<List<Brand>> getBrandList(Integer goodsTypeId);

    /**
     * 获取宠物试用期以及使用范围分类
     * @param petCategoryId
     * @return
     */
    ResultResponse<List<PetCategoryAndScopeVO>> getPetCategory(Integer categoryId,Integer petCategoryId);


    /**
     * 获取洗澡服务内容
     * @return
     */
    ResultResponse getBathingService();

    /**
     * 获取适用期和使用类型
     * @param goodsTypeId
     * @param brandId
     * @return
     */
    ResultResponse getScopeType(Integer goodsTypeId, Integer brandId);

    /**
     * 商品收藏/取消
     * @param userId
     * @param goodsId
     * @return
     */
    ResultResponse concernGoods(Integer userId, Integer goodsId);

    /**
     * 查看收藏商品列表
     * @param userId
     * @return
     */
    ResultResponse queryConcernGoodsList(Integer userId);
}
