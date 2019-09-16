package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-16 15:31
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LivingCollect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer livingId;

    //是否收藏 0 否 1 是
    private Integer enabledCollect;

    private Date createTime;

    private Date updateTime;

}
