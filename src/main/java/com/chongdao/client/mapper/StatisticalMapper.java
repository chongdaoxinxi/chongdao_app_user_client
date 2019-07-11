package com.chongdao.client.mapper;

import com.chongdao.client.vo.OrderStatisticalVO;
import com.chongdao.client.vo.UserVisitVO;
import com.chongdao.client.vo.UserWeekVisitVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface StatisticalMapper {
    UserVisitVO getUserVisitStatisticalShop(@Param("shopId") Integer shopId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    UserVisitVO getUserVisitStatisticalSystem(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    UserWeekVisitVO getUserWeekVisitStatisticalShop(@Param("shopId") Integer shopId, @Param("mondayStart") Date mondayStart, @Param("mondayEnd") Date mondayEnd, @Param("tuesdayEnd") Date tuesdayEnd, @Param("wednesdayEnd") Date wednesdayEnd, @Param("thursdayEnd") Date thursdayEnd, @Param("fridayEnd") Date fridayEnd, @Param("saturdayEnd") Date saturdayEnd, @Param("sundayEnd") Date sundayEnd);

    UserWeekVisitVO getUserWeekVisitStatisticalSystem(@Param("mondayStart") Date mondayStart, @Param("mondayEnd") Date mondayEnd, @Param("tuesdayEnd") Date tuesdayEnd, @Param("wednesdayEnd") Date wednesdayEnd, @Param("thursdayEnd") Date thursdayEnd, @Param("fridayEnd") Date fridayEnd, @Param("saturdayEnd") Date saturdayEnd, @Param("sundayEnd") Date sundayEnd);

    OrderStatisticalVO getOrderStatisticalShop(@Param("shopId") Integer shopId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    OrderStatisticalVO getOrderStatisticalSystem(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
