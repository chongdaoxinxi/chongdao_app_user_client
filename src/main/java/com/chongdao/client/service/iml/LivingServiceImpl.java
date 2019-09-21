package com.chongdao.client.service.iml;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.HtOrderInfo;
import com.chongdao.client.entitys.LivingInfo;
import com.chongdao.client.entitys.User;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import com.chongdao.client.enums.GoodsStatusEnum;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.freight.FreightComputer;
import com.chongdao.client.repository.HtOrderInfoRepository;
import com.chongdao.client.repository.LivingInfoRepository;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.LivingService;
import com.chongdao.client.utils.GenerateOrderNo;
import com.chongdao.client.vo.HTOrderInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fenglong
 * @date 2019-09-17 16:15
 */
@Service
public class LivingServiceImpl  extends CommonRepository implements LivingService {

    @Autowired
    private LivingInfoRepository livingInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HtOrderInfoRepository htOrderInfoRepository;

    @Autowired
    private FreightComputer freightComputer;

    /**
     * 预下单/下单
     * 订单类型 1：活体 2领养
     * 服务类型：2 单程 3到店自取
     * @param htOrderInfoVO
     * @return
     */
    @Override
    public ResultResponse preOrCreateOrder(HTOrderInfoVO htOrderInfoVO) {
        HtOrderInfo htOrderInfo = new HtOrderInfo();
        BeanUtils.copyProperties(htOrderInfoVO,htOrderInfo);

        //查找活体
        LivingInfo livingInfo = livingInfoRepository.findById(htOrderInfoVO.getLivingId()).orElse(null);
        if (livingInfo == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "该活体不存在，请重新选择");
        }
        //存储用户头像信息
        User user = userRepository.findById(htOrderInfoVO.getSellUserId()).orElse(null);
        if (user == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "卖家不存在，请重新选择");
        }
        htOrderInfoVO.setSellUserName(user.getName());
        htOrderInfoVO.setSellHeadIcon(user.getIcon());

        if (htOrderInfoVO.getServiceId() != null && htOrderInfoVO.getServiceId() > 0){
            CpnThresholdRule cpnThresholdRule = thresholdRuleRepository.findById(htOrderInfoVO.getServiceId()).get();
            if (cpnThresholdRule != null){
                htOrderInfoVO.setDiscountPrice(cpnThresholdRule.getMinPrice());
            }
        }

        //配送费(到店自取无配送费) 有优惠需减去优惠
        if (htOrderInfoVO.getServiceType() != 3 && htOrderInfoVO.getReceiveId() != null) {
            htOrderInfoVO.setServicePrice(freightComputer.computerFee(
                    htOrderInfoVO.getServiceType(), 1,
                    htOrderInfoVO.getReceiveId(), 0,
                    htOrderInfoVO.getSellUserId(), htOrderInfoVO.getBuyerUserId()
            ).subtract(htOrderInfoVO.getDiscountPrice()));
        }

        //订单总价
        htOrderInfo.setTotalPrice(livingInfo.getPrice().add(htOrderInfoVO.getServicePrice()));
        //实际付款
        htOrderInfo.setPayment(livingInfo.getPrice().add(htOrderInfoVO.getServicePrice()));
        //提交订单
        if (htOrderInfoVO.getCreateOrderType() == 2) {
            if (htOrderInfoVO.getReceiveId() == null || htOrderInfoVO.getReceiveTime() == null) {
                return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.ADDRESS_EMPTY.getStatus(), GoodsStatusEnum.ADDRESS_EMPTY.getMessage());
            }
            htOrderInfo.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
            htOrderInfo.setCreateTime(new Date());
            htOrderInfo.setUpdateTime(new Date());
            htOrderInfo.setHtOrderNo(GenerateOrderNo.genHTUniqueKey());
            htOrderInfoRepository.save(htOrderInfo);

        }
        return ResultResponse.createBySuccess(htOrderInfoVO);
    }
}
