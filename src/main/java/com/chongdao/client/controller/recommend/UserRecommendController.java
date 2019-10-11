package com.chongdao.client.controller.recommend;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.RecommendService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserRecommendController {
    @Autowired
    private RecommendService recommendService;

    /**
     * 生成推广链接
     *
     * @return
     */
    @PostMapping("initRecommendUrl")
    public ResultResponse initRecommendUrl(String token) throws Exception {
        // 推广员类型, 推广员id
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return recommendService.initRecommendUrl(tokenVo.getRole(), tokenVo.getUserId());
    }

    /**
     * 获取我的推广信息
     * @param token
     * @return
     */
    @PostMapping("getMyShareInfo")
    public ResultResponse getMyShareInfo(String token) throws Exception {
        return recommendService.getMyShareInfo(token);
    }

    /**
     * 校验用户是否已经登录过APP, 没有的进行更新(在app登录页面调用)
     * @param token
     * @return
     */
    @PostMapping("firstLoginAppCheck")
    public ResultResponse firstLoginAppCheck(String token) {
        return recommendService.firstLoginAppCheck(token);
    }

    /**
     * 获取我的推广人的消费记录
     * @param token
     * @param consumeType
     * @return
     */
    @PostMapping("getMyRecommendRecordData")
    public ResultResponse getMyRecommendRecordData(String token, Integer consumeType) {
        return recommendService.getMyRecommendRecordData(token, consumeType);
    }

    /**
     * 获取我推广的新用户数据
     * @param token
     * @return
     */
    @PostMapping("getMyRecommendUserData")
    public ResultResponse getMyRecommendUserData(String token) {
        return recommendService.getMyRecommendUserData(token);
    }

    /**
     * 获取推广返利排行
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getRecommendRankList")
    public ResultResponse getRecommendRankList(Integer pageNum, Integer pageSize) {
        return recommendService.getRecommendRankList(pageNum, pageSize);
    }

    /**
     * 获取我的邀请返利明细
     * @return
     */
    @PostMapping("getMyRecommendDetail")
    public ResultResponse getMyRecommendDetail(String token) {
        return recommendService.getMyRecommendDetail(token);
    }
}
