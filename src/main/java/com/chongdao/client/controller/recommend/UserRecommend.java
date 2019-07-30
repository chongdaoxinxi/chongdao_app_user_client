package com.chongdao.client.controller.recommend;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/30
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/recommend/user/")
public class UserRecommend {
    /**
     * 生成推广链接
     * @return
     */
    @GetMapping("initRecommendUrl")
    public ResultResponse initRecommendUrl(String token) {
        return null;
    }
}
