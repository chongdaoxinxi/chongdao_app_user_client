package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @Author onlineS
 * @Description 
 * @Date 17:45 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_info_re")
public class OrderInfoRe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderNo;
    private BigDecimal payment;
    private Integer userId;
    private Date createTime;
    private Date payTime;
    private Integer status;
    private String reOrderNo;
    private Integer shopId;
}