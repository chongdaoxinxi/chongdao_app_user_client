package com.chongdao.client.service.iml;

import com.chongdao.client.entitys.AreaBill;
import com.chongdao.client.service.AreaBillService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/26
 * @Version 1.0
 **/
@Service
public class AreaBillServiceImpl implements AreaBillService {

    @Override
    public List<AreaBill> getAreaBillByManagementId(Integer managementId, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageIndex) {
        return null;
    }
}
