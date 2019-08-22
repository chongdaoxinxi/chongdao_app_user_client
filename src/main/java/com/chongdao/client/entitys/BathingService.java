package com.chongdao.client.entitys;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 洗澡服务类
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BathingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Date createTime;

    private Date updateTime;

    @Transient
    private Boolean checked = false;

}
