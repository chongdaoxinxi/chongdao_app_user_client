package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.StatisticalService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/10
 * @Version 1.0
 **/
public class StatisticalServiceImpl implements StatisticalService {
    @Override
    public ResultResponse getUserVisitStatistical(String token, Integer type) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (StringUtils.isNotBlank(role)) {
            if(role.equals("ADMIN_PC")) {
                if(type == 1) {
                    // 每日

                } else if(type == 2) {
                    //每周
                }
            } else if(role.equals("SHOP_PC")) {

            }
        }
        return null;
    }

    @Override
    public ResultResponse getOrderStatistical(String token, Integer type) {
        return null;
    }

    @Override
    public ResultResponse getUserWeekVisitStatistical(String token, Integer type) {
        return null;
    }
}
