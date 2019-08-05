package com.chongdao.client.entitys;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description 运输保险理赔
 * @Author onlineS
 * @Date 2019/8/5
 * @Version 1.0
 **/
public class PetPickupInsuranceClaims implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
}
