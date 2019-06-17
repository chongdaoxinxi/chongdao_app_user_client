package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCategoryAndScopeVO {

    private Integer id;

    private String petCategoryName;

    private Integer scopeId;

    private String scopeName;
    private Integer petCategoryId;
}
