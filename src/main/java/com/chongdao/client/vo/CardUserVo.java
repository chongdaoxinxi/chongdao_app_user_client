package com.chongdao.client.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/5
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class CardUserVo {
    private Integer id;
    private String cardName;
    private String cardComment;
    private Date cardStartTime;
    private Date cardEndTime;
    private Integer cardType;//1:通用(官方);2:抵用券、免费洗;3.单程;4.双程5.公益

    private String couponName;
    private String couponCategoryName;
    private String couponShopName;
    private BigDecimal couponFullPrice;
    private BigDecimal couponDecreasePrice;
    private Date couponStartTime;
    private Date couponEndTime;
    private Integer couponType;//0:满减券(店铺活动),2:优惠券

    private Integer receive_count;//领取数量
}
