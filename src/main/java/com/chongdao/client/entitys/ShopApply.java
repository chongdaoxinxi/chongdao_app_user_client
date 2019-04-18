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
 * The persistent class for the shop_apply database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="shop_apply")
public class ShopApply extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Column(name="apply_money")
	private BigDecimal applyMoney;

	@Column(name="apply_note")
	private String applyNote;

	@Column(name="check_note")
	private String checkNote;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="check_time")
	private Date checkTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Column(name="realy_money")
	private BigDecimal realyMoney;

	@Column(name="shop_id")
	private Integer shopId;

	private Integer status;
	
    @Transient
    private String shopName;
    
    @Transient
    private String wxNo;
    
    @Transient
    private String zfbNo;

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