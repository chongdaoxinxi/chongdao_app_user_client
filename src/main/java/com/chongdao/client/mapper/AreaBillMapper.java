package com.chongdao.client.mapper;

import com.chongdao.client.entitys.AreaBill;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AreaBillMapper {
    List<AreaBill> getAreaBillByAreaCode(String areaCode, Date startDate, Date endDate);
}
