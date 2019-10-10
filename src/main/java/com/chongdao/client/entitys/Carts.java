package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "cart")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer goodsId;

    private Integer shopId;

    private Integer quantity;

    private Byte checked;

    private Date createTime;

    private Date updateTime;

    private Integer petCount = 0;

    public Carts(Integer id, Integer userId, Integer goodsId, Integer shopId, Integer quantity, Byte checked, Date createTime, Date updateTime,Integer petCount) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.shopId = shopId;
        this.quantity = quantity;
        this.checked = checked;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.petCount = petCount;
    }


}