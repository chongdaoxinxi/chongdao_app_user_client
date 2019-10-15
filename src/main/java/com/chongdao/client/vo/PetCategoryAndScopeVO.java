package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCategoryAndScopeVO {

    private Integer id;

    //适用类型名称 （如：全种类、小型犬、中型犬、大型犬） 注：猫粮没有适用类型
    private String petCategoryName;

    private Integer scopeId;

    private Integer brandId;

    //适用期名称(如：狗粮为：全期、幼犬、成犬、老年犬、繁育期、离乳期；猫粮为：全期、幼猫、成猫、老年猫、繁育期)
    private String scopeName;

    private Integer petCategoryId;
}
