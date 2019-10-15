package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 适用期
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ScopeApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer goodsTypeId;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    @Transient
    private List<PetCategory> petCategoryList;
}
