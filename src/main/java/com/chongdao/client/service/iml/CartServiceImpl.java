package com.chongdao.client.service.iml;

import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Carts;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.CartsMapper;
import com.chongdao.client.mapper.GoodMapper;
import com.chongdao.client.service.CartService;
import com.chongdao.client.utils.BigDecimalUtil;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.CartVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartsMapper cartsMapper;

    @Autowired
    private GoodMapper goodMapper;


    /**
     * 加入购物车,同时校验用户是否登录
     * @param userId
     * @param count
     * @param goodsId
     * @return
     */
    @Override
    public ResultResponse<CartVo> add(Integer userId, Integer count, Integer goodsId) {
        if (count == null || goodsId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //查询当前用户的购物车是否存在该商品
        Carts cart = cartsMapper.selectCartByUserIdAndGoodsId(userId,goodsId);
        if (cart == null){
            //这个产品不在这个购物车里,需要新增一个这个产品的记录
            Carts cartItem = new Carts();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setGoodsId(goodsId);
            cartItem.setUserId(userId);
            cartItem.setCreateTime(new Date());
            cartItem.setUpdateTime(new Date());
            cartsMapper.insert(cartItem);
        }else{
            //这个产品已经在购物车里了.
            //如果产品已存在,数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cart.setUpdateTime(new Date());
            cartsMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    /**
     * 查询购物车
     * @param userId
     * @return
     */
    @Override
    public ResultResponse<CartVo> list(Integer userId) {
        CartVo cartVo = getCartVoLimit(userId);
        return ResultResponse.createBySuccess(cartVo);
    }

    /**
     * 删除购物车
     * @param userId
     * @param goodsIds
     * @return
     */
    @Override
    public ResultResponse<CartVo> deleteGoods(Integer userId, String goodsIds) {
        List<String> goodsList = Splitter.on(",").splitToList(goodsIds);
        if (CollectionUtils.isEmpty(goodsList)){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        cartsMapper.deleteByUserIdAndProductIds(userId,goodsList);
        return this.list(userId);
    }


    /**
     * 根据userId封装当前购物车信息
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        //查询当前用户的购物车
        List<Carts> cartList = cartsMapper.selectCartByUserId(userId);
        List<CartGoodsVo> cartGoodsVoList = Lists.newArrayList();
        //购物车总价
        BigDecimal cartTotalPrice = new BigDecimal(BigInteger.ZERO);
        if (!CollectionUtils.isEmpty(cartList)){
            for (Carts cart : cartList) {
                CartGoodsVo cartGoodsVo = new CartGoodsVo();
                //将cart值拷贝到cartGoodsVo
                BeanUtils.copyProperties(cart, cartGoodsVo);
                //查询商品
                Good good = goodMapper.selectByPrimaryKey(cart.getGoodsId());
                if (good != null){
                    cartGoodsVo.setGoodsName(good.getName());
                    cartGoodsVo.setGoodsPrice(good.getPrice());
                    cartGoodsVo.setGoodsStatus(Integer.valueOf(good.getStatus()));
                    //用户购买的商品数量
                    cartGoodsVo.setQuantity(cart.getQuantity());
                    //计算总价
                    cartGoodsVo.setGoodsTotalPrice(BigDecimalUtil.mul(good.getPrice().doubleValue(), cart.getQuantity().doubleValue()));
                }
                //如果用户选中商品，则价格相加
                if (cart.getChecked() == Const.Cart.CHECKED){
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartGoodsVo.getGoodsTotalPrice().doubleValue());
                }
                cartGoodsVoList.add(cartGoodsVo);
            }

        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartGoodsVoList(cartGoodsVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        return cartVo;
    }

    /**
     * 获取所有选中状态的商品
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }
        return cartsMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
