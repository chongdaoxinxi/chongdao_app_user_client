package com.chongdao.client.entitys;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 使用种类
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer scopeId;

    //private Integer brandId;

    private Date createTime;

    private Date updateTime;

}
