package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderDetail;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.OrderDetailMapper;
import com.chongdao.client.mapper.OrderInfoMapper;
import com.chongdao.client.mapper.ShopMapper;
import com.chongdao.client.mapper.UserAddressMapper;
import com.chongdao.client.service.ExpressOrderService;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.vo.OrderGoodsVo;
import com.chongdao.client.vo.OrderVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/20
 * @Version 1.0
 **/
@Service
public class ExpressOrderServiceImpl implements ExpressOrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private UserAddressMapper addressMapper;

    /**
     * 获取订单列表(可接, 已接, 已完成)
     *
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getExpressOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize) {
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        PageHelper.startPage(pageNum, pageSize);
        if (type.equals("1")) {
            type = "2";//可接订单
        } else if (type.equals("2")) {
            type = "4,5,7,8,9,10,11,12,13";//已接订单
        } else if (type.equals("3")) {
            type = "3,6";//已完成订单
        } else {
            type = "";
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.selectExpressOrderList(expressId, type);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList,null);
        PageInfo pageResult = new PageInfo(orderInfoList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 获取配送管理员订单列表(商家已接单, 商家未接单)
     *
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getExpressAdminOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize) {
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        PageHelper.startPage(pageNum, pageSize);
        if(type.equals("all")) {
            type = null;
        } else if (type.equals("1")) {
            type = "2,3,4,5,6,7,8,9,10,11,12,13";//商家已接单
        } else if (type.equals("2")) {
            type = "-1,0,1";//商家未接单
        }else {
            type = "";
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.selectExpressAdminOrderList(type);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList,null);
        PageInfo pageResult = new PageInfo(orderInfoList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 接单
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse expressAcceptOrder(Integer expressId, Integer orderId) {
        return null;
    }

    /**
     * 取消订单(状态变为-1)-----------疑问 此方法存在合理嘛
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse cancelOrder(Integer expressId, Integer orderId) {
        return null;
    }

    /**
     * 到店
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse arriveShop(Integer expressId, Integer orderId) {
        return null;
    }

    /**
     * 服务完成
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse serviceComplete(Integer expressId, Integer orderId) {
        return null;
    }

    /**
     * 封装订单列表
     * @param orderList
     * @param userId
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<OrderInfo> orderList, Integer userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for(OrderInfo order : orderList){
            List<OrderDetail>  orderItemList = Lists.newArrayList();
            if(userId == null){
                //todo 管理员查询的时候 不需要传userId
                orderItemList = orderDetailMapper.getByOrderNo(order.getOrderNo());
            }else{
                orderItemList = orderDetailMapper.getByOrderNoUserId(order.getOrderNo(),userId);
            }
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }


    /**
     * 封装订单详情
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(OrderInfo order,List<OrderDetail> orderItemList){
        OrderVo orderVo = new OrderVo();
        //查询店铺
        Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
        BeanUtils.copyProperties(order,orderVo);
        orderVo.setShopName(shop.getShopName());
        orderVo.setShopLogo(shop.getLogo());
        //接宠地址
        UserAddress receiveAddress = addressMapper.selectByPrimaryKey(order.getReceiveAddressId());
        //送宠地址
        UserAddress deliverAddress = addressMapper.selectByPrimaryKey(order.getDeliverAddressId());
        if (receiveAddress != null){
            orderVo.setReceiveAddressName(receiveAddress.getLocation() + receiveAddress.getAddress());
        }
        if (deliverAddress != null){
            orderVo.setDeliverAddressName(deliverAddress.getLocation() + deliverAddress.getAddress());
        }
        //订单明细
        List<OrderGoodsVo> orderGoodsVoList = Lists.newArrayList();
        orderItemList.forEach(orderDetail -> {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            orderGoodsVo.setGoodsId(orderDetail.getId());
            orderGoodsVo.setCreateTime(DateTimeUtil.dateToStr(orderDetail.getCreateTime()));
            orderGoodsVo.setGoodsName(orderDetail.getName());
            orderGoodsVo.setGoodsIcon(orderDetail.getIcon());
            orderGoodsVo.setCurrentPrice(orderDetail.getCurrentPrice());
            orderGoodsVo.setGoodsPrice(orderDetail.getPrice());
            orderGoodsVo.setQuantity(orderDetail.getCount());
            orderGoodsVo.setTotalPrice(orderDetail.getTotalPrice());
            orderGoodsVoList.add(orderGoodsVo);
        });
        orderVo.setOrderGoodsVoList(orderGoodsVoList);
        return orderVo;
    }
}
