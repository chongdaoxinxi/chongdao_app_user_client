package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 商家提现
 * @Date 9:10 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="shop_apply")
public class ShopApply extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="shop_id")
	private Integer shopId;
	@Transient
	private String shopName;
	@Column(name="apply_money")
	private BigDecimal applyMoney;//申请提现金额
	@Column(name="apply_note")
	private String applyNote;//申请备注
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="check_time")
	private Date checkTime;//审核时间
	@Column(name="check_note")
	private String checkNote;//审核备注
	@Column(name="realy_money")
	private BigDecimal realyMoney;//真实提现金额
	@Transient
    private String wxNo;
	@Transient
    private String zfbNo;
	private Integer status;//
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;//申请创建时间

	@Override
	public String toString() {
		return "ShopApply{" +
				"id=" + id +
				", applyMoney=" + applyMoney +
				", applyNote='" + applyNote + '\'' +
				", checkNote='" + checkNote + '\'' +
				", checkTime=" + checkTime +
				", createdate=" + createdate +
				", realyMoney=" + realyMoney +
				", shopId=" + shopId +
				", status=" + status +
				", shopName='" + shopName + '\'' +
				", wxNo='" + wxNo + '\'' +
				", zfbNo='" + zfbNo + '\'' +
				'}';
	}
}