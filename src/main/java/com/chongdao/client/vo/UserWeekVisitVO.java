package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
public class UserWeekVisitVO {
    private Integer mondayVisitCount;
    private Integer tuesdayVisitCount;
    private Integer wednesdayVisitCount;
    private Integer thursdayVisitCount;
    private Integer fridayVisitCount;
    private Integer saturdayVisitCount;
    private Integer sundayVisitCount;
    private Date startDay;
    private Date endDay;
}
