package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="assistance_info")
public class AssistanceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer shareUserId;
    private String shareUserOpenid;
    private String shareUrl;
    private Integer assistancePower;
    private Date shareTime;
    private Integer version;

    @Override
    public String toString() {
        return "AssistanceInfo{" +
                "id=" + id +
                ", shareUserId=" + shareUserId +
                ", shareUserOpenid='" + shareUserOpenid + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", assistancePower=" + assistancePower +
                ", shareTime=" + shareTime +
                ", version=" + version +
                '}';
    }
}
