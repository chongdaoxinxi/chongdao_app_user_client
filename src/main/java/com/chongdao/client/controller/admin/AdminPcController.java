package com.chongdao.client.controller.admin;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Banner;
import com.chongdao.client.entitys.Express;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.service.*;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 管理员pc端
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/admin_pc/")
public class AdminPcController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShopApplyService shopApplyService;
    @Autowired
    private ShopManageService shopManageService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopBillService shopBillService;
    @Autowired
    private AreaWithdrawalApplyService areaWithdrawalApplyService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private StatisticalService statisticalService;
    @Autowired
    private ExpressRuleService expressRuleService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private UserWithdrawalService userWithdrawalService;

    /**
     * 确认退款完成
     *
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("confirmRefund")
    public ResultResponse confirmRefund(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals(RoleEnum.ADMIN_PC.getCode())) {
            return orderService.adminConfirmRefund(orderId);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 获取退款记录
     *
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("getRefundData")
    public ResultResponse getRefundData(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null) {
            return orderService.getRefundData(orderId);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 获取订单评论
     *
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("getEvalData")
    public ResultResponse getEvalData(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null) {
            return orderService.getOrderEvalData(orderId);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 获取商家提现记录
     *
     * @param token
     * @param shopName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getShopWithdrawalList")
    public ResultResponse getShopWithdrawalList(String token, String shopName, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals(RoleEnum.SUPER_ADMIN_PC.getCode())) {
            if(role.equals(RoleEnum.SUPER_ADMIN_PC.getCode())) {
                return shopApplyService.getShopApplyList(null, shopName, status, startDate, endDate, pageNum, pageSize);
            } else if(role.equals(RoleEnum.SHOP_PC.getCode()) || role.equals(RoleEnum.SHOP_APP.getCode())){
                return shopApplyService.getShopApplyList(tokenVo.getUserId(), shopName, status, startDate, endDate, pageNum, pageSize);
            }
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
        return ResultResponse.createByErrorMessage("参数错误!");
    }

    /**
     * 获取地区账户提现记录
     *
     * @param token
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getAreaWithdrawalList")
    public ResultResponse getAreaWithdrawalList(String token, String name, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null) {
            if (role.equals(RoleEnum.ADMIN_PC.getCode())) {
                //地区管理员
                return areaWithdrawalApplyService.getAreaWithdrawApplyListData(tokenVo.getUserId(), name, status, startDate, endDate, pageNum, pageSize);
            } else if (role.equals(RoleEnum.SUPER_ADMIN_PC.getCode())) {
                //超级管理员
                return areaWithdrawalApplyService.getAreaWithdrawApplyListData(null, name, status, startDate, endDate, pageNum, pageSize);
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
    }

    /**
     * 获取管理员信息
     *
     * @param token
     * @return
     */
    @GetMapping("getManagementData")
    public ResultResponse getManagementData(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals(RoleEnum.ADMIN_PC.getCode())) {
            return managementService.getManagementById(tokenVo.getUserId());
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 同意商家提现
     *
     * @param token
     * @param shopApplyId
     * @param money
     * @param checkNote
     * @return
     */
    @PostMapping("acceptWithdrawal")
    public ResultResponse acceptWithdrawal(String token, Integer shopApplyId, BigDecimal money, String checkNote) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && (role.equals(RoleEnum.SUPER_ADMIN_PC.getCode()) || role.equals(RoleEnum.ADMIN_PC.getCode()))) {
            return shopApplyService.acceptShopApplyRecord(shopApplyId, money, checkNote);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 拒绝商家提现
     *
     * @param token
     * @param shopApplyId
     * @param checkNote
     * @return
     */
    @PostMapping("refuseWithdrawal")
    public ResultResponse refuseWithdrawal(String token, Integer shopApplyId, String checkNote) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && (role.equals(RoleEnum.SUPER_ADMIN_PC.getCode()) || role.equals(RoleEnum.ADMIN_PC.getCode()))) {
            return shopApplyService.refuseShopApplyRecord(shopApplyId, checkNote);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 获取商店详细数据
     *
     * @param token
     * @param shopId
     * @return
     */
    @GetMapping("getShopInfo")
    public ResultResponse getShopInfo(String token, Integer shopId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals(RoleEnum.ADMIN_PC.getCode())) {
            return shopManageService.getShopInfo(shopId);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 获取地区商店列表
     *
     * @param token
     * @param shopName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getShopDataList")
    public ResultResponse getShopDataList(String token, String shopName, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals(RoleEnum.ADMIN_PC.getCode())) {
            return shopService.getShopDataList(tokenVo.getUserId(), shopName, pageNum, pageSize);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 添加商店
     *
     * @param shop
     * @return
     */
    @PostMapping("addShop")
    public ResultResponse addShop(Shop shop) {
        return shopService.addShop(shop);
    }

    @GetMapping("getParentAreaIdBySonAreaId")
    public ResultResponse getParentAreaIdBySonAreaId(Integer sonAreaId) {
        return areaService.getParentAreaIdBySonAreaId(sonAreaId);
    }

    /**
     * 获取一级市
     *
     * @param token
     * @param level
     * @param isOpen
     * @return
     */
    @GetMapping("getTopLevelAreaDataList")
    public ResultResponse getTopLevelAreaDataList(String token, Integer level, Integer isOpen) {
        return areaService.getTopLevelAreaDataList(level, isOpen);
    }

    /**
     * 根据父id, 级联获取下级地区
     *
     * @param token
     * @param level
     * @param isOpen
     * @param pid
     * @return
     */
    @GetMapping("getAreaDataByParentId")
    public ResultResponse getAreaDataByParentId(String token, Integer level, Integer isOpen, Integer pid) {
        return areaService.getAreaDataByParentId(level, isOpen, pid);
    }

    /**
     * 获取商家的流水记录
     *
     * @param token
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getShopBillByShopId")
    public ResultResponse getShopBillByShopId(String token, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopBillService.getShopBillByShopId(tokenVo.getUserId(), startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取区域内商家的所有流水记录
     *
     * @param token
     * @param shopName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getShopBillByAreaCode")
    public ResultResponse getShopBillByAreaCode(String token, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopBillService.getShopBillByAreaCode(tokenVo.getUserId(), shopName, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取地区账户流水记录
     *
     * @param token
     * @param shopName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getAreaBill")
    public ResultResponse getAreaBill(String token, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopBillService.getShopBillByAreaCode(tokenVo.getUserId(), shopName, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取使用过优惠券的订单运输险投保疲累
     *
     * @param token
     * @param shopName
     * @param orderNo
     * @param username
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getConcessionalOrderListAdmin")
    public ResultResponse getConcessionalOrderListAdmin(String token, String shopName, String orderNo, String username, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return orderService.getConcessionalOrderList(token, shopName, orderNo, username, phone, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取首页统计数据
     *
     * @param token
     * @param dateType
     * @return
     */
    @GetMapping("getStatisticalHomeData")
    public ResultResponse getStatisticalHomeData(String token, Integer dateType) {
        return statisticalService.getStatisticalData(token, dateType);
    }

    /**
     * 获取配送规则(根据管理员token)
     *
     * @param token
     * @return
     */
    @GetMapping("getExpressRule")
    public ResultResponse getExpressRule(String token) {
        return expressRuleService.getExpressRule(token);
    }

    /**
     * 新增/保存配送规则
     *
     * @param token
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("saveExpressRule")
    public ResultResponse saveExpressRule(String token, String startTime, String endTime) {
        return expressRuleService.saveExpressRule(token, startTime, endTime);
    }

    /**
     * 获取管理员城市信息
     *
     * @param token
     * @return
     */
    @GetMapping("getManagementCityInfo")
    public ResultResponse getManagementCityInfo(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return managementService.getManagementById(tokenVo.getUserId());
    }

    /**
     * 获取配送员数据
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getExpressList")
    public ResultResponse getExpressList(String token, String expressName, Integer selectType, Integer selectStatus, Integer pageNum, Integer pageSize) {
        return expressService.getExpressList(token, expressName, selectType, selectStatus, pageNum, pageSize);
    }

    /**
     * 保存配送员信息
     *
     * @param express
     * @return
     */
    @PostMapping("saveExpress")
    public ResultResponse saveExpress(Express express) {
        return expressService.saveExpress(express);
    }

    /**
     * 删除配送员
     *
     * @param expressId
     * @return
     */
    @GetMapping("deleteExpress")
    public ResultResponse deleteExpress(Integer expressId) {
        return expressService.removeExpress(expressId);
    }

    /**
     * 获取轮播图数据列表
     *
     * @param token
     * @param status
     * @return
     */
    @GetMapping("getBannerList")
    public ResultResponse getBannerList(String token, Integer status) {
        return bannerService.getBannerList(token, status);
    }

    /**
     * 保存轮播图
     *
     * @param banner
     * @return
     */
    @PostMapping("saveBanner")
    public ResultResponse saveBanner(Banner banner) {
        return bannerService.saveBanner(banner);
    }

    /**
     * 删除轮播图
     *
     * @param id
     * @return
     */
    @GetMapping("deleteBanner")
    public ResultResponse deleteBanner(Integer id) {
        return bannerService.deleteBanner(id);
    }

    /**
     * 获取用户提现记录列表
     * @param token
     * @param name
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getUserWithdrawalList")
    public ResultResponse getUserWithdrawalList(String token, String name, String phone, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return userWithdrawalService.getUserWithdrawalList(null, name, phone, status, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 地区账户申请提现
     * @param token
     * @param applyMoney
     * @param applyNote
     * @return
     */
    @PostMapping("applyAreaWithdrawal")
    public ResultResponse applyAreaWithdrawal(String token, BigDecimal applyMoney, String applyNote) {
        return areaWithdrawalApplyService.applyAreaWithdrawal(token, applyMoney, applyNote);
    }

    /**
     * 审核用户提现
     *
     * @param userWithdrawalId
     * @param note
     * @param realMoney
     * @param targetStatus
     * @return
     */
    @PostMapping("checkUserWithdrawal")
        public ResultResponse checkUserWithdrawal(Integer userWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus) {
        return userWithdrawalService.checkUserWithdrawal(userWithdrawalId, note, realMoney, targetStatus);
    }

    /**
     * 审核地区账户提现
     * @param areaWithdrawalId
     * @param note
     * @param realMoney
     * @param targetStatus
     * @return
     */
    @PostMapping("checkAreaWithdrawal")
    public ResultResponse checkAreaWithdrawal(Integer areaWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus) {
        return  areaWithdrawalApplyService.checkAreaWithdrawal(areaWithdrawalId, note, realMoney, targetStatus);
    }
}
