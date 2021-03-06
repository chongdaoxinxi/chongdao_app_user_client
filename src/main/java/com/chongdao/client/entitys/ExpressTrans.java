package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/9/6
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class ExpressTrans implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer expressId;
    private Integer recommendRecordId;
    private BigDecimal money;
    private Integer type;//1:医疗险;2:家责险;3:新用户返现;
    private String comment;
    private Date createTime;
}
