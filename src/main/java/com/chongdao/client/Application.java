package com.chongdao.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.chongdao.client.mapper")
@EnableScheduling
//@EnableFeignClients
//@EnableCircuitBreaker
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        System.out.println("testStrToDate:================" + DateTimeUtil.strToDate("20191012163726"));
    }

}
