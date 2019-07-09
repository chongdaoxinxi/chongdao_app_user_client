package com.chongdao.client.service;

import com.chongdao.client.entitys.AreaBill;

import java.util.Date;
import java.util.List;

public interface AreaBillService {
    List<AreaBill> getAreaBillByManagementId(Integer managementId, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageSize);
}
