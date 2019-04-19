package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
    @GeneratedValue
    private Integer id;
    private String areaCode;//区域码
    private String startTime;//工作开始时间
    private String endTime;//工作结束时间

    @Override
    public String toString() {
        return "ExpressRule{" +
                "id=" + id +
                ", areaCode='" + areaCode + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}