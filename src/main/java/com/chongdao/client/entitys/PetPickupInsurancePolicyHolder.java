package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description 运输险投保人(后台通过订单信息自动生成)
 * @Author onlineS
 * @Date 2019/5/29
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class PetPickupInsurancePolicyHolder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
}
