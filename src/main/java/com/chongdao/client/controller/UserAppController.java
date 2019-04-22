package com.chongdao.client.controller;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.UserLoginVO;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/4/19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/userApp")
public class UserAppController {

    /**
     * 获取商家列表
     * @return
     */
    @RequestMapping("/getShopList")
    public ResultResponse<UserLoginVO> getShopList(){
        return null;
    }

    /**
     * 获取轮播图
     * @return
     */
    @RequestMapping("/getBanner")
    public ResultResponse<UserLoginVO> getBanner(){
        return null;
    }

    /**
     * 获取分享数据
     * @return
     */
    @RequestMapping("/getShareInfo")
    public ResultResponse<UserLoginVO> getShareInfo(){
        return null;
    }

    /**
     * 用户分享
     * @return
     */
    @RequestMapping("/userShare")
    public ResultResponse<UserLoginVO> userShare(){
        return null;
    }

    /**
     * 获取商家评论
     * @return
     */
    @RequestMapping("/getShopEval")
    public ResultResponse<UserLoginVO> getShopEval(){
        return null;
    }

    /**
     * 获取商家明细信息
     * @return
     */
    @RequestMapping("/getShopDetailById")
    public ResultResponse<UserLoginVO> getShopDetailById(){
        return null;
    }

    /**
     * 获取用户地址数据
     * @return
     */
    @RequestMapping("/getUserAddress")
    public ResultResponse<UserLoginVO> getUserAddress(){
        return null;
    }

    /**
     * 计算订单费用
     * @return
     */
    @RequestMapping("/calculateOrderCost")
    public ResultResponse<UserLoginVO> calculateOrderCost() {
        return null;
    }

    /**
     * 获取商品详细数据
     * @return
     */
    @RequestMapping("/getGoodDetail")
    public ResultResponse<UserLoginVO> getGoodDetail() {
        return null;
    }

    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping("/getGoodsList")
    public ResultResponse<UserLoginVO> getGoodsList() {
        return null;
    }

    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    public ResultResponse<UserLoginVO> getOrderList() {
        return null;
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/getUserDetail")
    public ResultResponse<UserLoginVO> getUserDetail() {
        return null;
    }

    /**
     * 获取用户待办信息
     * @return
     */
    @RequestMapping("/getUseTodoMessage")
    public ResultResponse<UserLoginVO> getUserTodoMessage() {
        return null;
    }

    /**
     * 获取礼包
     * @return
     */
    @RequestMapping("/getPackage")
    public ResultResponse<UserLoginVO> getPackage() {
        return null;
    }

    /**
     * 获取用户充值记录
     * @return
     */
    @RequestMapping("/getUserRechargeList")
    public ResultResponse<UserLoginVO> getUserRechargeList() {
        return null;
    }

    /**
     * 用户充值
     * @return
     */
    @RequestMapping("/userRecharge")
    public ResultResponse<UserLoginVO> userRecharge() {
        return null;
    }

    /**
     * 获取用户账户信息
     * @return
     */
    @RequestMapping("/getUserAccount")
    public ResultResponse<UserLoginVO> getUserAccount() {
        return null;
    }

    /**
     * 获取用户卡券信息(卡包)
     * @return
     */
    @RequestMapping("/getUserCardList")
    public ResultResponse<UserLoginVO> getUserCardList() {
        return null;
    }

    /**
     * 获取用户收藏列表
     * @return
     */
    @RequestMapping("/getUserFavouriteList")
    public ResultResponse<UserLoginVO> getUserFavouriteList() {
        return null;
    }

    /**
     * 获取开放区域列表
     * @return
     */
    @RequestMapping("/getOpenAreaList")
    public ResultResponse<UserLoginVO> getOpenAreaList() {
        return null;
    }

    /**
     * 保存用户配置
     * @return
     */
    @RequestMapping("/saveUserSetting")
    public ResultResponse<UserLoginVO> saveUserSetting() {
        return null;
    }

    /**
     * 保存用户的宠物卡片
     * @return
     */
    @RequestMapping("/savePetCard")
    public ResultResponse<UserLoginVO> savePetCard() {
        return null;
    }
}
