package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_delivery")
public class OrderDelivery implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private Integer orderId;

    private Integer courierId;

    private Date createTime;

    private String note;

    private Integer type;

    @Override
    public String toString() {
        return "OrderDelivery{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", courierId=" + courierId +
                ", createTime=" + createTime +
                ", note='" + note + '\'' +
                ", type=" + type +
                '}';
    }
}