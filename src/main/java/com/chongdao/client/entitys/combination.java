package com.chongdao.client.entitys;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/** 
 * @Author onlineS
 * @Description
 * @Date 12:44 2019/4/19
 */
@Setter
@Getter
@NoArgsConstructor
public class combination implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer shopid;
    private Integer count;
    private  String orderNo;

    @Override
    public String toString() {
        return "combination{" +
                "shopid=" + shopid +
                ", count=" + count +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
