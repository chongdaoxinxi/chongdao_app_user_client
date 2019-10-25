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
 * @Description 保险组队抽奖 参与队员实体
 * @Author onlineS
 * @Date 2019/10/25
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceTeamAttender implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;//参加者id
    private String name;//参加者名称
    private Integer builderId;//发起人Id
    private Integer teamId;//队伍Id
    private Integer type;// 1:发起人, 2:参与人
    private Integer status;// 0:待确认(通过链接注册, 但还未下载app确认), 1:已确认
    private Integer isWin;//-1: 未中奖, 0:未开奖, 1:中奖
    private Date attendTime;//确认参加组队时间
    private Date createTime;
}
