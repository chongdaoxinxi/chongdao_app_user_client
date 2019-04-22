package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @Author onlineS
 * @Description 账单日志(暂时废弃)
 * @Date 17:45 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "log_account")
public class LogAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private BigDecimal money;
    private Integer accountId;
    private String note;
    private Date createTime;
    private Integer type;
    private Integer connId;

    @Override
    public String toString() {
        return "LogAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", money=" + money +
                ", accountId=" + accountId +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", connId=" + connId +
                '}';
    }
}