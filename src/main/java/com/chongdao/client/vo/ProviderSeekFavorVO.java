package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-30 17:05
 */
@Getter
@Setter
public class ProviderSeekFavorVO {

    private Integer userId;

    private String icon;

    private Date createTime;

    private String userName;

    //是否给予奖励 0 1
    private Integer status;

    //该字段作为标识使用（判断查看寻宠详情是否是失主本人，如果不是 "给予奖励" 按钮消失） 0 显示  1 不显示
    private Integer enabled = 1;
}
