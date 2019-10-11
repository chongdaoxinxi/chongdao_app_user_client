package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface RecommendService {
    /**
     * 生成推广信息
     * @param role
     * @param id
     * @return
     */
    ResultResponse initRecommendUrl(String role, Integer id) throws Exception;

    /**
     * 获取我的推广信息
     * @param token
     * @return
     */
    ResultResponse getMyShareInfo(String token) throws Exception;

    /**
     * 校验订单是否满足返现要求
     * @param orderId
     * @return
     */
    boolean isSatisfyOrderRewardQualification(Integer orderId);

    /**
     * 推广新用户首单完成时返现(首单完成时调用)
     * @param orderId
     * @return
     */
    ResultResponse recommendUserFirstOrder(Integer orderId);

    /**
     * 推广新用户首单返现之后发生退款时的逻辑(首单退款完成时调用)
     * @param orderId
     * @return
     */
    ResultResponse refundOrderDeductReward(Integer orderId);

    ResultResponse firstLoginAppCheck(String token);

    /**
     * 保险订单完成生成推广记录
     * @param insuranceOrderId
     * @return
     */
    boolean recommendInsuranceOrder(Integer insuranceOrderId);

    /**
     * 获取我的推广消费记录
     * @param token
     * @param consumeType
     * @return
     */
    ResultResponse getMyRecommendRecordData(String token, Integer consumeType);

    /**
     * 获取我的推广新用户记录
     * @param token
     * @return
     */
    ResultResponse getMyRecommendUserData(String token);

    /**
     * 获取推广返利排行
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getRecommendRankList(Integer pageNum, Integer pageSize);

    /**
     * 获取我的推广返利记录
     * @param token
     * @return
     */
    ResultResponse getMyRecommendDetail(String token);
}
