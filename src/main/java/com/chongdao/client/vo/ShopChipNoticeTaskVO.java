package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/10
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopChipNoticeTaskVO {
    Integer shopId;//商店Id
    String shopName;//商店名称
    String phone;//联系电话
    Integer leftChipCount;//剩余宠物芯片数量
}
