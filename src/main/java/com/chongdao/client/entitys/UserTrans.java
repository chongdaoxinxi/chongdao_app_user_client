package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 用户金额交易记录
 * @Date 9:33 2019/4/19
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="user_trans")
public class UserTrans implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer userid;
	private Integer orderid;
	private String comment;//注释
	private BigDecimal money;
	private Integer type;//1:用户充值, 2:用户消费, 3:拒单退还

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
}