package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @Author onlineS
 * @Description 配送员规则
 * @Date 17:37 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="express_rule")
public class ExpressRule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String areaCode;//区域码
    private Time startTime;//工作开始时间
    private Time endTime;//工作结束时间
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
}