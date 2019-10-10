package com.chongdao.client.task;

import com.chongdao.client.mapper.InsuranceShopChipMapper;
import com.chongdao.client.vo.ShopChipNoticeTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/10
 * @Version 1.0
 **/
@Component
@Slf4j
public class ShopChipNoticeTask {
    @Autowired
    private InsuranceShopChipMapper insuranceShopChipMapper;

    /**
     * 定时提醒宠物芯片可用数量低于10的商家补充(整点运行)
     */
    @Async
    @Scheduled(cron="0 0 23 * * ?")
    //@Scheduled(cron="0 */1 * * * ?")
    public void closeCouponTask(){
        List<ShopChipNoticeTaskVO> needNoticeShopList = insuranceShopChipMapper.getNeedNoticeShopList();
        //拿到需要通知的医院类商家后, 发出通知
        //或者是短信, 或者是推送
    }
}
