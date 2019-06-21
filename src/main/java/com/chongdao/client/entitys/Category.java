package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String remark;

    private Integer status;

    private Integer type;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    public Category(Integer id, String name, String remark, Integer status,Integer type, Integer sort, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.status = status;
        this.type = type;
        this.sort = sort;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


}