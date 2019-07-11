package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.StatisticalMapper;
import com.chongdao.client.service.StatisticalService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.StatisticalDateUtil;
import com.chongdao.client.vo.OrderStatisticalVO;
import com.chongdao.client.vo.ResultTokenVo;
import com.chongdao.client.vo.UserVisitVO;
import com.chongdao.client.vo.UserWeekVisitVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/10
 * @Version 1.0
 **/
@Service
public class StatisticalServiceImpl implements StatisticalService {
    @Autowired
    private StatisticalMapper statisticalMapper;

    @Override
    public ResultResponse getUserVisitStatistical(String token, Integer dateType) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), getUserVisitStatisticalData(token, dateType));
    }

    private UserVisitVO getUserVisitStatisticalData(String token, Integer dateType) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (StringUtils.isNotBlank(role)) {
            if (role.equals("ADMIN_PC")) {
                if (dateType == 1) {
                    // 每日
                    return statisticalMapper.getUserVisitStatisticalSystem(StatisticalDateUtil.getTimesMorning(), StatisticalDateUtil.getTimesNight());
                } else if (dateType == 2) {
                    //每周
                    return statisticalMapper.getUserVisitStatisticalSystem(StatisticalDateUtil.getTimesWeekMorning(), StatisticalDateUtil.getTimesWeekNight());
                }
            } else if (role.equals("SHOP_PC")) {
                if (dateType == 1) {
                    // 每日
                    return statisticalMapper.getUserVisitStatisticalShop(tokenVo.getUserId(), StatisticalDateUtil.getTimesMorning(), StatisticalDateUtil.getTimesNight());
                } else if (dateType == 2) {
                    //每周
                    return statisticalMapper.getUserVisitStatisticalShop(tokenVo.getUserId(), StatisticalDateUtil.getTimesWeekMorning(), StatisticalDateUtil.getTimesWeekNight());
                }
            }
        }
        return null;
    }

    @Override
    public ResultResponse getOrderStatistical(String token, Integer dateType) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), getOrderStatisticalData(token, dateType));
    }

    private OrderStatisticalVO getOrderStatisticalData(String token, Integer dateType) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (StringUtils.isNotBlank(role)) {
            if (role.equals("ADMIN_PC")) {
                if (dateType == 1) {
                    // 每日
                    return statisticalMapper.getOrderStatisticalSystem(StatisticalDateUtil.getTimesMorning(), StatisticalDateUtil.getTimesNight());
                } else if (dateType == 2) {
                    //每周
                    return statisticalMapper.getOrderStatisticalSystem(StatisticalDateUtil.getTimesWeekMorning(), StatisticalDateUtil.getTimesWeekNight());
                }
            } else if (role.equals("SHOP_PC")) {
                if (dateType == 1) {
                    // 每日
                    return statisticalMapper.getOrderStatisticalShop(tokenVo.getUserId(), StatisticalDateUtil.getTimesMorning(), StatisticalDateUtil.getTimesNight());
                } else if (dateType == 2) {
                    //每周
                    return statisticalMapper.getOrderStatisticalShop(tokenVo.getUserId(), StatisticalDateUtil.getTimesWeekMorning(), StatisticalDateUtil.getTimesWeekNight());
                }
            }
        }
        return null;
    }

    @Override
    public ResultResponse getUserWeekVisitStatistical(String token, Integer dateType) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), getUserWeekVisitStatisticalData(token, dateType));
    }

    private UserWeekVisitVO getUserWeekVisitStatisticalData(String token, Integer dateType) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (StringUtils.isNotBlank(role)) {
            if (role.equals("ADMIN_PC")) {
                return statisticalMapper.getUserWeekVisitStatisticalSystem(StatisticalDateUtil.getTimesWeekMorning(), StatisticalDateUtil.getTimesTuesdayMorning(), StatisticalDateUtil.getTimesWednesdayMorning(), StatisticalDateUtil.getTimesThursdayMorning(), StatisticalDateUtil.getTimesFridayMorning(), StatisticalDateUtil.getTimesSaturdayMorning(), StatisticalDateUtil.getTimesSunMorning(), StatisticalDateUtil.getTimesWeekNight());
            } else if (role.equals("SHOP_PC")) {
               return statisticalMapper.getUserWeekVisitStatisticalShop(tokenVo.getUserId(), StatisticalDateUtil.getTimesWeekMorning(), StatisticalDateUtil.getTimesTuesdayMorning(), StatisticalDateUtil.getTimesWednesdayMorning(), StatisticalDateUtil.getTimesThursdayMorning(), StatisticalDateUtil.getTimesFridayMorning(), StatisticalDateUtil.getTimesSaturdayMorning(), StatisticalDateUtil.getTimesSunMorning(), StatisticalDateUtil.getTimesWeekNight());
            }
        }
        return null;
    }

    @Override
    public ResultResponse getStatisticalData(String token, Integer dateType) {
        Map<String, Object> resp = new HashMap<>();
        //获取userVisit统计数据
        resp.put("userVisit", getUserVisitStatisticalData(token, dateType));
        //获取orderStatistical统计数据
        resp.put("orderStatistical", getOrderStatisticalData(token, dateType));
        //获取userWeekStatistical统计数据
        resp.put("userWeekStatisticalData", getUserWeekVisitStatisticalData(token, dateType));
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), resp);
    }
}
