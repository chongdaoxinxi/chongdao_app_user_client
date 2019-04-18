package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_wechat_info_server")
public class UserWechatInfoServer extends PageParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String openid;

    private String nickname;

    private Integer sex;

    private String city;

    private String country;

    private String province;

    private String language;

    private String headimgurl;

    private String subscribeTime;

    private String unionid;

    private String remark;

    private Integer groupid;

    private String subscribe;

    private String gender;

    private String privilege;

    private String appOpenid;

    private Date createTime;

    @Override
    public String toString() {
        return "UserWechatInfoServer{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", language='" + language + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribeTime='" + subscribeTime + '\'' +
                ", unionid='" + unionid + '\'' +
                ", remark='" + remark + '\'' +
                ", groupid=" + groupid +
                ", subscribe='" + subscribe + '\'' +
                ", gender='" + gender + '\'' +
                ", privilege='" + privilege + '\'' +
                ", appOpenid='" + appOpenid + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}