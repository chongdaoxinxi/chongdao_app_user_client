package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.UserShopVisit;
import com.chongdao.client.entitys.UserSystemVisit;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.OrderInfoRepository;
import com.chongdao.client.repository.UserShopVisitRepository;
import com.chongdao.client.repository.UserSystemVisitRepository;
import com.chongdao.client.service.UserVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/9
 * @Version 1.0
 **/
@Service
public class UserVisitServiceImpl implements UserVisitService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private UserShopVisitRepository userShopVisitRepository;
    @Autowired
    private UserSystemVisitRepository userSystemVisitRepository;

    @Override
    public ResultResponse addUserShopVisit(Integer userId, Integer shopId, Integer source) {
        UserShopVisit usv = new UserShopVisit();
        usv.setUserId(userId);
        usv.setShopId(shopId);
        usv.setIsOld(isOldOfShop(userId, shopId));
        usv.setSource(source);
        usv.setCreateTime(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userShopVisitRepository.saveAndFlush(usv));
    }

    @Override
    public ResultResponse addUserSystemVisit(Integer userId, Integer isOld, Integer source) {
        UserSystemVisit usv = new UserSystemVisit();
        usv.setUserId(userId);
        usv.setIsOld(isOld);
        usv.setSource(source);
        usv.setCreateTime(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userSystemVisitRepository.saveAndFlush(usv));
    }

    private Integer isOldOfShop(Integer userId, Integer shopId) {
        List<OrderInfo> list = orderInfoRepository.findByUserIdAndShopId(userId, shopId).orElse(null);
        if(list != null && list.size() > 0) {
            return 1;
        }
        return -1;
    }
}
