package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.mapper.GoodMapper;
import com.chongdao.client.mapper.ShopMapper;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.BigDecimalUtil;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.CartVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 预下单
     * @param cartVo
     * @return
     */
    @Override
    public ResultResponse<CartVo> preOrder(CartVo cartVo) {
        List<CartGoodsVo> cartGoodsVoList = Lists.newArrayList();
        //订单总价
        BigDecimal cartTotalPrice = new BigDecimal(BigInteger.ZERO);
        if (!CollectionUtils.isEmpty(cartVo.getCartGoodsVoList())){
            for (CartGoodsVo e : cartVo.getCartGoodsVoList()) {
                if (e.getUserId() == null || e.getGoodsId() == null || e.getShopId() == null){
                    throw new PetException(ResultEnum.PARAM_ERROR);
                }
                CartGoodsVo cartGoodsVo = new CartGoodsVo();
                //查询商品
                Good good = goodMapper.selectByPrimaryKey(e.getGoodsId());
                //查询店铺
                Shop shop = shopMapper.selectByPrimaryKey(good.getShopId());
                cartGoodsVo.setShopName(shop.getShopName());
                if (good != null){
                    cartGoodsVo.setGoodsName(good.getName());
                    cartGoodsVo.setGoodsPrice(good.getPrice());
                    cartGoodsVo.setGoodsStatus(Integer.valueOf(good.getStatus()));
                    //用户购买的商品数量
                    cartGoodsVo.setQuantity(e.getQuantity());
                    //计算总价
                    cartGoodsVo.setGoodsTotalPrice(BigDecimalUtil.mul(good.getPrice().doubleValue(), e.getQuantity().doubleValue()));
                }
                cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartGoodsVo.getGoodsTotalPrice().doubleValue());
                cartGoodsVoList.add(cartGoodsVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartGoodsVoList(cartGoodsVoList);
        return ResultResponse.createBySuccess(cartVo);
    }
}
