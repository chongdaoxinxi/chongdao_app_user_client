package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 保险组队抽奖 队伍实体
 * @Author onlineS
 * @Date 2019/10/25
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceTeam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer builderId;//发起人
    private String builderName;//发起人姓名
    private String url;//分享url
    private String qrCodeUrl;//分享二维码地址
    private Integer visitTimes;//该分享链接被访问次数
    private Integer status;//-1:已关闭; 0:待确认; 1:正常运行
    private Date createTime;//创建时间
}
