package com.chongdao.client.controller.recommend;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.RecommendService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RecommendService recommendService;

    /**
     * 生成推广链接
     *
     * @return
     */
    @GetMapping("initRecommendUrl")
    public ResultResponse initRecommendUrl(String token) {
        // 推广员类型, 推广员id
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return recommendService.initRecommendUrl(tokenVo.getRole(), tokenVo.getUserId());
    }

    /**
     * 获取我的推广信息
     * @param token
     * @return
     */
    @GetMapping("getMyShareInfo")
    public ResultResponse getMyShareInfo(String token) {
        return recommendService.getMyShareInfo(token);
    }

    /**
     * 校验用户是否已经登录过APP, 没有的进行更新(在app登录页面调用)
     * @param token
     * @return
     */
    @GetMapping("firstLoginAppCheck")
    public ResultResponse firstLoginAppCheck(String token) {
        return recommendService.firstLoginAppCheck(token);
    }

    /**
     * 获取我的推广消费记录
     * @param token
     * @param consumeType
     * @return
     */
    @GetMapping("getMyRecommendRecordData")
    public ResultResponse getMyRecommendRecordData(String token, Integer consumeType) {
        return recommendService.getMyRecommendRecordData(token, consumeType);
    }

    /**
     * 获取我的推广新用户记录
     * @param token
     * @return
     */
    @GetMapping("getMyRecommendUserData")
    public ResultResponse getMyRecommendUserData(String token) {
        return recommendService.getMyRecommendUserData(token);
    }
}
