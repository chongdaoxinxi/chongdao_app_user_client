package com.chongdao.client.entitys;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/27
 * @Version 1.0
 **/
public class ShopSignInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

}
