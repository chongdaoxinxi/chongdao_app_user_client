package com.chongdao.client.task;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author fenglong
 * @date 2019-06-20 16:41
 * 关闭优惠券任务
 */
@Component
@Slf4j
public class CloseCouponTask extends CommonRepository {

    @Scheduled(cron="0 0 23 * * ?")
    //@Scheduled(cron="0 */1 * * * ?")
    public void closeCouponTask(){
        log.info("【优惠券】定时任务开始...");
        //查询所有已发布的优惠券
        Iterable<CouponInfo> couponInfoList = couponInfoRepository.findAllByCpnState(1);
        couponInfoList.forEach(e -> {
            //状态为已发布
            if (e.getCpnState() == 1) {
                //查询截止日期与当前日期差
                long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(e.getValidityEndDate()),
                        DateTimeUtil.dateToStr(new Date()));
                if (result <= 0) {
                    e.setCpnState(-1);
                    log.error(String.valueOf(e.getCpnState()));
                }
            }
        });
        couponInfoRepository.saveAll(couponInfoList);
        log.info("【优惠券】定时任务结束...");

    }


}
