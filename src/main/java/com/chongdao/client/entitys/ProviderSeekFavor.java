package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-30 16:53
 */
@Getter
@Setter
@Entity
public class ProviderSeekFavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //提供丢失宠物线索用户id
    private Integer providerUserId;

    //丢失宠物用户id
    private Integer lostUserId;

    //活体id
    private Integer livingId;

    //是否给予奖励 0 1
    private Integer status = 1;


    private Date createTime;

    private Date updateTime;
}
