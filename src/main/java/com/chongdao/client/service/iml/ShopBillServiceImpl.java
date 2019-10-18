package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.entitys.ShopBill;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.ShopBillMapper;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.repository.ShopBillRepository;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.service.ShopBillService;
import com.chongdao.client.vo.OrderShopVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        OrderShopVO orderVo = orderService.getOrderDetailByOrderId(shopBill.getOrderId());
        BigDecimal price = shopBill.getPrice();
        orderVo.setRealInMoney(price);//实际收款
        BigDecimal deductPrice = orderVo.getGoodsPrice().subtract(price);
        BigDecimal deduct = deductPrice.divide(orderVo.getGoodsPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        orderVo.setDeduct(deduct);//扣费比例
        return ResultResponse.createBySuccess(orderVo);
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
    public ResultResponse getShopBillByShopId(Integer shopId, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShopBill> list = shopBillMapper.getShopBillByShopId(shopId, startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }
}
