package com.chongdao.client.entitys;



import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/** 
 * @Author onlineS
 * @Description 充值规则(暂时废弃)
 * @Date 9:05 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "recharge_rule")
public class RechargeRule extends PageParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    private BigDecimal rechargeMoney;
    private BigDecimal giveMoney;
    private String startTime;
    private String endTime;
    private Integer userNum;
    private Integer status;
    private String areaCode;
    private String note;
    private Integer pid;
    private String title;
    private String subTitle;
    private String activityImg;
    @Transient
    private String areaName;

    @Override
    public String toString() {
        return "RechargeRule{" +
                "id=" + id +
                ", rechargeMoney=" + rechargeMoney +
                ", giveMoney=" + giveMoney +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", userNum=" + userNum +
                ", status=" + status +
                ", areaCode='" + areaCode + '\'' +
                ", note='" + note + '\'' +
                ", pid=" + pid +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", activityImg='" + activityImg + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}