package com.chongdao.client.controller.admin;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.*;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        if (role != null && role.equals("ADMIN_PC")) {
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

    @GetMapping("getWithdrawalList")
    public ResultResponse getWithdrawalList(String token, String shopName, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals("ADMIN_PC")) {
            return shopApplyService.getShopApplyList(shopName, pageNum, pageSize);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 同意提现
     *
     * @param token
     * @param shopApplyId
     * @param money
     * @param checkNote
     * @return
     */
    @GetMapping("acceptWithdrawal")
    public ResultResponse acceptWithdrawal(String token, Integer shopApplyId, BigDecimal money, String checkNote) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals("ADMIN_PC")) {
            return shopApplyService.acceptShopApplyRecord(shopApplyId, money, checkNote);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    /**
     * 拒绝提现
     *
     * @param token
     * @param shopApplyId
     * @param checkNote
     * @return
     */
    @GetMapping("refuseWithdrawal")
    public ResultResponse refuseWithdrawal(String token, Integer shopApplyId, String checkNote) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals("ADMIN_PC")) {
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
        if (role != null && role.equals("ADMIN_PC")) {
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
        if (role != null && role.equals("ADMIN_PC")) {
            return shopService.getShopDataList(tokenVo.getUserId(), shopName, pageNum, pageSize);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
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
     * @param pageIndex
     * @return
     */
    @GetMapping("getShopBillByShopId")
    public ResultResponse getShopBillByShopId(String token, Date startDate, Date endDate, Integer pageNum, Integer pageIndex) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopBillService.getShopBillByShopId(tokenVo.getUserId(), startDate, endDate, pageNum, pageIndex);
    }

    /**
     * 获取区域内商家的所有流水记录
     *
     * @param token
     * @param shopName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageIndex
     * @return
     */
    @GetMapping("getShopBillByAreaCode")
    public ResultResponse getShopBillByAreaCode(String token, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageIndex) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopBillService.getShopBillByAreaCode(tokenVo.getUserId(), shopName, startDate, endDate, pageNum, pageIndex);
    }

    /**
     * 获取地区账户流水记录
     *
     * @param token
     * @param shopName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageIndex
     * @return
     */
    @GetMapping("getAreaBill")
    public ResultResponse getAreaBill(String token, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageIndex) {
//        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
//        return shopBillService.getShopBillByAreaCode(tokenVo.getUserId(), shopName, startDate, endDate, pageNum, pageIndex);
        return null;
    }

    /**
     * 获取使用过优惠券的订单
     *
     * @param token
     * @param orderNo
     * @param username
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageIndex
     * @return
     */
    @GetMapping("getOnSaleOrderListData")
    public ResultResponse getOnSaleOrderListData(String token, String orderNo, String username, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageIndex) {
        return orderService.getOnSaleOrderListData(token, orderNo, username, phone, startDate, endDate, pageNum, pageIndex);
    }
}
