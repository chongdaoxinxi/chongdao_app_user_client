package com.chongdao.client.controller.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;
import com.chongdao.client.entitys.InsuranceFeeRecord;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.*;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @Description 商家PC端
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_pc/")
public class ShopPcController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private ShopBillService shopBillService;
    @Autowired
    private ShopApplyService shopApplyService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private InsuranceFeeRecordService insuranceFeeRecordService;
    @Autowired
    private ShopChipService shopChipService;
    @Autowired
    private GoodTasteService goodTasteService;

    @GetMapping("getMyDetailInfo")
    public ResultResponse getMyDetailInfo(String token) {
        return shopService.getShopInfo(token);
    }

    @GetMapping("getModuleData")
    public ResultResponse getModuleData(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null) {
            return moduleService.getModuleData();
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    @GetMapping("getCategoryData")
    public ResultResponse getCategoryData(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals("SHOP_PC")) {
            return categoryService.getCategoryData();
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
        }
    }

    @RequestMapping(value = "addGoodsType", method = RequestMethod.POST)
    @ResponseBody
    public ResultResponse addGoodsType(GoodsType goodsType) {
        return goodsTypeService.addGoodsType(goodsType);
//        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
//        String role = tokenVo.getRole();
//        if(role != null && role.equals("SHOP_PC")) {
//            return goodsTypeService.addGoodsType(goodsType);
//        } else {
//            return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
//        }
    }

    /**
     * 获取规格单位
     *
     * @param moduleId
     * @param categoryId
     * @return
     */
    @GetMapping("getUnitList")
    public ResultResponse getUnitList(Integer moduleId, Integer categoryId) {
        return unitService.getUnitList(moduleId, categoryId);
    }

    @GetMapping("getGoodTasteList")
    public ResultResponse getGoodTasteList() {
        return goodTasteService.getGoodTasteList();
    }

    /**
     * getGoodCategoryList
     *
     * @param moduleId
     * @param categoryId
     * @return
     */
    @GetMapping("getSelectGoodsTypeSpecialConfig")
    public ResultResponse getSelectGoodsTypeSpecialConfig(Integer moduleId, Integer categoryId, Integer goodsTypeId) {
        return goodsTypeService.getSelectGoodsTypeSpecialConfig(moduleId, categoryId, goodsTypeId);
    }

    /**
     * 获取商店的流水记录
     *
     * @param token
     * @param startDate
     * @param endDate
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("getShopBillListData")
    public ResultResponse getShopBillListData(String token, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopBillService.getShopBillByShopId(tokenVo.getUserId(), startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取商店提现记录
     *
     * @param token
     * @param startDate
     * @param endDate
     * @param pageSize
     * @param pageNum
     * @return
     */
    @PostMapping("getShopApplyListData")
    public ResultResponse getShopApplyListData(String token, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopApplyService.getShopApplyList(tokenVo.getUserId(), null, startDate, endDate, pageNum, pageSize);
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
     * @param pageSize
     * @return
     */
    @GetMapping("getConcessionalOrderListShop")
    public ResultResponse getConcessionalOrderListShop(String token, String orderNo, String username, String phone, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return orderService.getConcessionalOrderList(token, null, orderNo, username, phone, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取店铺各类型订单消费明细
     *
     * @param token
     * @param type(1:配送商品订单,2:配送服务订单,3:到店订单)
     * @param userName
     * @param orderNo
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getMoneyOrderList")
    public ResultResponse getMoneyOrderList(String token, Integer type, String userName, String orderNo, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 获取店铺医疗费用消费明细
     *
     * @param token
     * @param userName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getInsuranceFeeRecordData")
    public ResultResponse getInsuranceFeeRecordData(String token, String userName, String shopName, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return insuranceFeeRecordService.getInsuranceFeeRecordData(token, userName, shopName, status, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 添加保险医疗费用记录
     * @return
     */
    @PostMapping("addInsuranceFeeRecord")
    public ResultResponse addInsuranceFeeRecord(@RequestBody InsuranceFeeRecord insuranceFeeRecord) {
        return null;
    }

    /**
     * 获取店铺推广奖励明细
     *
     * @param token
     * @param userName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getMoneyRecommendList")
    public ResultResponse getMoneyRecommendList(String token, String userName, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 导入宠物芯片数据
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("importShopChipData")
    public ResultResponse importShopChipData(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        return shopChipService.importShopChipData(request.getHeader("token"), file);
    }

    /**
     * 获取宠物芯片数据列表
     * @return
     */
    @PostMapping("getShopChipData")
    public ResultResponse getShopChipData(String token, String core, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return shopChipService.getShopChipData(token, core, status, startDate, endDate, pageNum, pageSize);
    }
}