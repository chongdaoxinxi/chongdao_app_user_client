package com.chongdao.client.vo;

import com.chongdao.client.entitys.Brand;
import com.chongdao.client.entitys.GoodsType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author fenglong
 * @date 2019-07-26 09:27
 */
@Setter
@Getter
public class BrandGoodsTypeVO {
    private Integer goodsTypeId;
    private List<Brand> brandList;
    private Integer categoryId;
    private String goodsTypeName;
    private Integer parentId;
    private String imgUrl;
    private Integer brandId;

    private List<GoodsType> goodsTypeList;
}
