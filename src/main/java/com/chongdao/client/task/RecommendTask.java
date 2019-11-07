package com.chongdao.client.task;

import com.chongdao.client.repository.OrderInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/11/7
 * @Version 1.0
 **/
@Component
@Slf4j
public class RecommendTask {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Async
    @Scheduled(cron="0 0 2 * * ?")
    public void recommendOrder() {

    }
}
