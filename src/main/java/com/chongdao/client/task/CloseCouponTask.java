package com.chongdao.client.task;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.entitys.OrderEval;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CpnUser;
import com.chongdao.client.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author fenglong
 * @date 2019-06-20 16:41
 * 关闭优惠券任务
 */
@Component
@Slf4j
public class CloseCouponTask extends CommonRepository {

    @Async
    @Scheduled(cron="0 0 23 * * ?")
    //@Scheduled(cron="0 */1 * * * ?")
    public void closeCouponTask(){
        log.info("【优惠券CouponInfo】定时任务开始...");
        //查询所有已发布的优惠券
        Iterable<CouponInfo> couponInfoList = couponInfoRepository.findAllByCpnState(1);
        couponInfoList.forEach(e -> {
            //状态为已发布
            if (e.getCpnState() == 1) {
                //查询截止日期与当前日期差
                long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(e.getValidityEndDate()),
                        DateTimeUtil.dateToStr(new Date()));
                if (result <= 0) {
                    //下架
                    e.setCpnState(2);
                    log.error(String.valueOf(e.getCpnState()));
                }
            }
        });
        couponInfoRepository.saveAll(couponInfoList);
        log.info("【优惠券CouponInfo】定时任务结束...");
    }


    @Async
    @Scheduled(cron="0 0 23 * * ?")
    //@Scheduled(cron="0 */1 * * * ?")
    public void closeCpnUserTask(){
        log.info("【优惠券CpnUser】定时任务开始...");
        //查询所有已发布的优惠券
        Iterable<CpnUser> cpnUserList = cpnUserRepository.findAllByUserCpnState(0);
        cpnUserList.forEach(e -> {
            //状态为已发布
            if (e.getUserCpnState() == 0) {
                //查询截止日期与当前日期差
                long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(e.getValidityEndDate()),
                        DateTimeUtil.dateToStr(new Date()));
                if (result <= 0) {
                    e.setUserCpnState(2);
                    log.error(String.valueOf(e.getUserCpnState()));
                }
            }
        });
        cpnUserRepository.saveAll(cpnUserList);
        log.info("【优惠券CpnUser】定时任务结束...");
    }

    @Async
    @Scheduled(cron="0 0 11 * * ?")
    public void genarateOrderEval() {
        List<OrderEval> list = orderEvalRepository.findAll();
        int i = 0;
        for(OrderEval e : list) {
            OrderEval oe = new OrderEval();
            oe.setOrderNo(e.getOrderNo());
            oe.setUserId(e.getUserId());
            oe.setShopId(e.getShopId());
            oe.setStatus(1);
            oe.setContent("该用户默认好评");
            oe.setGrade(5);
            oe.setCreateTime(new Date());
            oe.setUpdateTime(new Date());
            orderEvalRepository.save(oe);

            //更新订单状态为6
            OrderInfo orderInfo = orderInfoRepository.findByOrderNo(oe.getOrderNo());
            orderInfo.setOrderStatus(6);
            orderInfoRepository.save(orderInfo);

            //保存之后, 更新对应商店评分
            Shop shop = shopRepository.findById(e.getShopId()).get();
            Double grade = shop.getGrade();
            if(grade != null) {
                shop.setGrade((grade + Double.valueOf((double) oe.getGrade()))/2.0D);
            } else {
                shop.setGrade(oe.getGrade()*1.0D);
            }
            shopRepository.save(shop);

        }
    }

}
