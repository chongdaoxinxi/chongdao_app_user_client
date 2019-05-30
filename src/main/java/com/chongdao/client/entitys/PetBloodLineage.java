package com.chongdao.client.entitys;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description 宠物血统
 * @Author onlineS
 * @Date 2019/5/30
 * @Version 1.0
 **/
public class PetBloodLineage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
}
