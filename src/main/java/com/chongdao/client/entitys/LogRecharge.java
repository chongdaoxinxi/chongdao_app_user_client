package com.chongdao.client.entitys;


import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "log_recharge")
public class LogRecharge extends PageParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private Integer payType;

    private BigDecimal rechargeMoney;

    private BigDecimal receiveMoney;

    private Integer userId;

    private Integer connId;

    private Integer connPid;

    private Integer connType;

    private String orderCode;

    private String payNo;

    private Date createTime;

    private Date payTime;

    private Integer status;

    @Transient
    private String userName;
    @Transient
    private String startTime;
    @Transient
    private String endTime;
    @Transient
    private BigDecimal ruleMoney;
    @Transient
    private BigDecimal giveMoney;
    @Transient
    private String areaCode;

    @Override
    public String toString() {
        return "LogRecharge{" +
                "id=" + id +
                ", payType=" + payType +
                ", rechargeMoney=" + rechargeMoney +
                ", receiveMoney=" + receiveMoney +
                ", userId=" + userId +
                ", connId=" + connId +
                ", connPid=" + connPid +
                ", connType=" + connType +
                ", orderCode='" + orderCode + '\'' +
                ", payNo='" + payNo + '\'' +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", ruleMoney=" + ruleMoney +
                ", giveMoney=" + giveMoney +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}