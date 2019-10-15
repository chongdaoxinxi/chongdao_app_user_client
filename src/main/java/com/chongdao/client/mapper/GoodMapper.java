package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Good;
import com.chongdao.client.vo.GoodsPcVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface GoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);


    /**
     * 查询所有上架状态的商品
     * @return
     */
    List<Good> selectList();

    List<Good> selectByName(@Param("goodsName") String goodsName, @Param("brandId")Integer brandId,@Param("goodsTypeId")Integer goodsTypeId,
                            @Param("scopeIds")String scopeIds,@Param("petCategoryId")Integer petCategoryId,
                            @Param("areaCode")  String areaCode,
                            @Param("orderBy") String orderBy);

    List<Good> getFavouriteGoodList(@Param("userId") Integer userId);

    List<Good> selectListByShopId(@Param("shopId")Integer shopId);

    List<Good> selectByShopIdAndCategoryId(@Param("shopId") Integer shopId, @Param("categoryId")Integer categoryId);



    //----------------------------------商户端------------------------------------------//
    List<GoodsPcVO> getGoodList(@Param("shopId")Integer shopId, @Param("goodsTypeId") Integer goodsTypeId, @Param("goodName") String goodName);


    void updateGoodsStatus(@Param("goodId") Integer goodId, @Param("status") Integer status);

    void goodsDiscount(@Param("shopId")Integer shopId, @Param("goodsTypeId")Integer goodsTypeId, @Param("discount")Double discount,@Param("reDiscount") Double reDiscount);

    int paymentNumber(@Param("goodId") Integer goodId);

    List<Good> findByShopIdAndGoodsTypeId(@Param("shopId") Integer shopId, @Param("id") Integer id);

    List<Good> findByShopIdAndStatus(Integer shopId);


    void updateDiscount(@Param("shopId") Integer shopId,@Param("ids")  List<Integer> ids,
                        @Param("discount") Double discount, @Param("reDiscount") Double reDiscount);

    List<Good> findOfficialShopGoodsList(@Param("goodsId") String goodsId, @Param("shopId") Integer shopId);
}