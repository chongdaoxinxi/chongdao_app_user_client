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
@Table(name="assistance_item")
public class AssistanceItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer assistanceUserId;
    private String assistanceUserOpenid;
    private String assistanceUserName;
    private Integer shareUserId;
    private String shareUserOpenid;
    private Date assistanceTime;

    @Override
    public String toString() {
        return "AssistanceItem{" +
                "id=" + id +
                ", assistanceUserId=" + assistanceUserId +
                ", assistanceUserOpenid='" + assistanceUserOpenid + '\'' +
                ", assistanceUserName='" + assistanceUserName + '\'' +
                ", shareUserId=" + shareUserId +
                ", shareUserOpenid='" + shareUserOpenid + '\'' +
                ", assistanceTime=" + assistanceTime +
                '}';
    }
}
