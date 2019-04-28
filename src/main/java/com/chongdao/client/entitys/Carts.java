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

    private Integer quantity;

    private Byte checked;

    private Date createTime;

    private Date updateTime;

    public Carts(Integer id, Integer userId, Integer goodsId, Integer quantity, Byte checked, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.checked = checked;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


}