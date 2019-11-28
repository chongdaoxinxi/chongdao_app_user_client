package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.ShopBillMapper;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.service.ShopBillService;
import com.chongdao.client.vo.ShopBillDetailVO;
import com.chongdao.client.vo.ShopBillVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/13
 * @Version 1.0
 **/
@Service
public class ShopBillServiceImpl implements ShopBillService {
    @Autowired
    private ShopBillRepository shopBillRepository;
    @Autowired
    private ShopBillMapper shopBillMapper;
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InsuranceFeeRecordRepository insuranceFeeRecordRepository;

    @Override
    public ResultResponse addShopBillRecord(Integer orderId, Integer shopId, Integer type, String note, BigDecimal realMoney) {
        ShopBill sb = new ShopBill();
        sb.setShopId(shopId);
        if (orderId != null) {
            sb.setOrderId(orderId);
        } else {
            sb.setOrderId(0);
        }
        sb.setPrice(realMoney);
        sb.setType(type);
        sb.setNote(note);
        sb.setCreateTime(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopBillRepository.saveAndFlush(sb));
    }

    /**
     * 获取指定区域的流水记录
     *
     * @param managementId
     * @param shopName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse getShopBillByAreaCode(Integer managementId, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Management management = managementRepository.findById(managementId).orElse(null);
        List<ShopBill> list = shopBillMapper.getShopBillByAreaCode(management.getAreaCode(), shopName, startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse getShopBillOrderDetailById(Integer shopBillId) {
        ShopBill shopBill = shopBillRepository.findById(shopBillId).orElse(null);
        ShopBillDetailVO sbdVo = new ShopBillDetailVO();
        BeanUtils.copyProperties(shopBill, sbdVo);
        Integer orderId = shopBill.getOrderId();
        Integer type = shopBill.getType();
        if(orderId != null) {
            if(type == 1 || type == 2) {
                OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
                BigDecimal goodsPrice = orderInfo.getGoodsPrice();
                Integer orderStatus = orderInfo.getOrderStatus();
                sbdVo.setOrderPrice(goodsPrice);
                sbdVo.setOrderNo(orderInfo.getOrderNo());
                sbdVo.setStatus(orderStatus);
            }
        }
//        OrderShopVO orderVo = orderService.getOrderDetailByOrderId(shopBill.getOrderId());
//        BigDecimal price = shopBill.getPrice();
//        orderVo.setRealInMoney(price);//实际收款
//        BigDecimal deductPrice = orderVo.getGoodsPrice().subtract(price);
//        BigDecimal deduct = deductPrice.divide(orderVo.getGoodsPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
//        orderVo.setDeduct(deduct);//扣费比例
        return ResultResponse.createBySuccess(sbdVo);
    }

    /**
     * 获取指定商家的流水记录
     *
     * @param shopId
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse getShopBillByShopId(Integer shopId, Date startDate, Date endDate, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ShopBillVO> list = shopBillMapper.getShopBillByShopId(shopId, startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }
}
