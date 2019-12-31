package com.chongdao.client.controller.recommend;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.InsuranceTeamService;
import com.chongdao.client.service.RecommendService;
import com.chongdao.client.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private InsuranceTeamService insuranceTeamService;

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

    /**
         * 获取推广注册链接中推广人的信息
     * @param userId
     * @return
     */
    @PostMapping("getMyRecommenderInfo")
    public ResultResponse getMyRecommenderInfo(Integer userId) {
        return userService.getUserSettingInfo(userId);
    }


    ////////////////////////////组队/////////////////////////////////////

    /**
     * 注册并发起组队
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("signAndBuildInsuranceTeam")
    public ResultResponse signAndBuildInsuranceTeam(String phone, String code) throws Exception {
        return insuranceTeamService.signAndBuildInsuranceTeam(phone, code);
    }

    /**
     * 访问组队分享页面
     * @return
     */
    @PostMapping("visitRecommendUrl")
    public ResultResponse visitRecommendUrl(Integer builderId) {
        return insuranceTeamService.visitRecommendUrl(builderId);
    }

    /**
     * 注册并参加组队
     * @return
     */
    @PostMapping("visitRecommendUrl")
    public ResultResponse signAndAttendInsuranceTeam(String phone, String code, Integer teamId) {
        return insuranceTeamService.signAndAttendInsuranceTeam(phone, code, teamId);
    }

    /**
     * 获取我待确认的组队
     * @param userId
     * @return
     */
    @PostMapping("getMyTodoAttend")
    public ResultResponse getMyTodoAttend(Integer userId) {
        return insuranceTeamService.getMyTodoAttend(userId);
    }

    /**
     * 确认参加活动
     * @param attenderId
     */
    @PostMapping("confirmAttend")
    public ResultResponse confirmAttend(Integer attenderId) {
        return insuranceTeamService.confirmAttend(attenderId);
    }

    /**
     * 获取我的组队详情
     * @param builderId
     * @return
     */
    @PostMapping("getAttendDetail")
    public ResultResponse getAttendDetail(Integer builderId) {
        return insuranceTeamService.getAttendDetail(builderId);
    }

    /**
     * 开奖
     * @return
     */
    @PostMapping("systemLuckyDraw")
    public ResultResponse systemLuckyDraw(Integer teamId) {
        return insuranceTeamService.systemLuckyDraw(teamId);
    }
}
