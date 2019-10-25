package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface InsuranceTeamService {
    /**
     * 发起组队
     * @param builderId
     */
    ResultResponse buildInsuranceTeam(Integer builderId);

    /**
     * 访问组队分享页面
     * @param builderId
     */
    ResultResponse visitRecommendUrl(Integer builderId);

    /**
     * 参加组队
     * @param teamId
     * @param attenderId
     */
    ResultResponse attendInsuranceTeam(Integer teamId, Integer attenderId);

    /**
     * 获取我待确认的组队
     * @param userId
     * @return
     */
    ResultResponse getMyTodoAttend(Integer userId);

    /**
     * 确认参加活动
     * @param attenderId
     */
    ResultResponse confirmAttend(Integer attenderId);

    /**
     * 获取我的组队详情
     * @param builderId
     * @return
     */
    ResultResponse getAttendDetail(Integer builderId);
}
