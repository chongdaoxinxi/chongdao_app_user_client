package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/25
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
public class SimpleOrderEvalVO {
    private String orderNo;
    private Integer userId;
    private String userName;
    private String content;
    private Date createTime;
}
