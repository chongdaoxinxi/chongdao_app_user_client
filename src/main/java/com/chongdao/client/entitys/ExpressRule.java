package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="express_rule")
public class ExpressRule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String areaCode;

    private String startTime;

    private String endTime;

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