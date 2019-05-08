package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsTypeVO {

    private Integer shopId;

    private Integer categoryId;

    private String goodsTypeName;

    private Integer categoryName;

    private Integer goodsTypeId;

    private List<GoodsListVO> goodsListVOList;
}
