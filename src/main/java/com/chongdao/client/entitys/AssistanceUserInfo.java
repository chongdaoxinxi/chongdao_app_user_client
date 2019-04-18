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
@Table(name="assistance_user_info")
public class AssistanceUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String openid;
    private String token;
    private String name;
    private String headImgUrl;
    private Date createTime;

    @Override
    public String toString() {
        return "AssistanceUserInfo{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
