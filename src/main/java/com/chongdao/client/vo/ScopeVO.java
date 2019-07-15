package com.chongdao.client.vo;

import com.chongdao.client.entitys.PetCategory;
import com.chongdao.client.entitys.ScopeApplication;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author fenglong
 * @date 2019-07-15 15:22
 */
@Getter
@Setter
public class ScopeVO {

    private List<PetCategory> petCategoryList;

    private List<ScopeApplication> scopeApplicationList;
}
