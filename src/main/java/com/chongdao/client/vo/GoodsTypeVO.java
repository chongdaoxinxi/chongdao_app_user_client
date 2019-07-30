package com.chongdao.client.vo;

import com.chongdao.client.entitys.PetCard;
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

    private Integer sort;

    private Integer status;

    private Integer moduleId;

    private List<GoodsListVO> goodsListVOList;

    private List<PetCard> petCardList;
}
