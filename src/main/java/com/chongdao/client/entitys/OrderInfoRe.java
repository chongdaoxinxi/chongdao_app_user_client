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
@Table(name = "order_info_re")
public class OrderInfoRe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private Integer orderId;

    private String payUser;

    private Date createTime;

    private Date payTime;

    private Integer status;

    private String orderNo;

    private String oldOrderNo;

    @Override
    public String toString() {
        return "OrderInfoRe{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", payUser='" + payUser + '\'' +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", status=" + status +
                ", orderNo='" + orderNo + '\'' +
                ", oldOrderNo='" + oldOrderNo + '\'' +
                '}';
    }
}