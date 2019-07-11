package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/10
 * @Version 1.0
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class UserVisitVO {
    private Integer visitTime;//人次
    private Integer visitUserCount;//人数
    private Integer newUserVisitTime;
    private Integer oldUserVisitTime;
    private Integer acceptedOrderCount;
    private Integer refundOrderCount;
//    private Integer type;//1:每日;2:每周;3:每月;4:每季;5:每年;
}
