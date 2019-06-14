package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.ShopApply;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.ShopApplyRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.ShopApplyService;
import com.chongdao.client.service.ShopBillService;
import com.chongdao.client.service.ShopService;
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
public class ShopApplyServiceImpl implements ShopApplyService {
    @Autowired
    private ShopApplyRepository shopApplyRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopBillService shopBillService;

    /**
     * 添加提现记录
     * @param shopId
     * @param applyMoney
     * @param applyNote
     * @return
     */
    @Override
    public ResultResponse addShopApplyRecord(Integer shopId, BigDecimal applyMoney, String applyNote) {
        Shop s = shopRepository.findById(shopId).orElse(null);
        ShopApply sa = new ShopApply();
        sa.setShopId(s.getId());
        sa.setApplyMoney(applyMoney);
        sa.setApplyNote(applyNote);
        sa.setStatus(0);
        sa.setCreateTime(new Date());
        sa.setUpdateTime(new Date());
        //扣除余额
        shopService.updateShopMoney(shopId, applyMoney.multiply(new BigDecimal(-1)));
        //TODO 短信通知管理员
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopApplyRepository.saveAndFlush(sa));
    }

    /**
     * 同意提现(打款之后操作)
     * @param shopApplyId
     * @param realMoney
     * @param checkNote
     * @return
     */
    @Override
    public ResultResponse acceptShopApplyRecord(Integer shopApplyId, BigDecimal realMoney, String checkNote) {
        ShopApply sa = shopApplyRepository.findById(shopApplyId).orElse(null);
        sa.setCheckNote(checkNote);
        sa.setCheckTime(new Date());
        sa.setRealMoney(realMoney);
        sa.setStatus(1);
        sa.setUpdateTime(new Date());
        //添加流水记录
        shopBillService.addShopBillRecord(sa.getShopId(), 0, 3, "店铺提现", sa.getApplyMoney().multiply(new BigDecimal(-1)));
        //TODO 短信通知商铺

        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopApplyRepository.saveAndFlush(sa));
    }

    /**
     * 拒绝提现
     * @param shopApplyId
     * @param checkNote
     * @return
     */
    @Override
    public ResultResponse refuseShopApplyRecord(Integer shopApplyId, String checkNote) {
        ShopApply sa = shopApplyRepository.findById(shopApplyId).orElse(null);
        sa.setCheckNote(checkNote);
        sa.setCheckTime(new Date());
        sa.setUpdateTime(new Date());
        sa.setStatus(-1);
        //退还余额
        Integer shopId = sa.getShopId();
        shopService.updateShopMoney(shopId, sa.getApplyMoney());
        //TODO 短信通知商铺

        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopApplyRepository.saveAndFlush(sa));
    }

    @Override
    public ResultResponse getShopApplyList(String shopName, Integer pageNum, Integer pageSize) {
        List<ShopApply> shopAppliesByShopName = shopApplyRepository.getShopAppliesByShopName(shopName, pageNum * pageSize, (pageNum + 1) * pageSize);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopApplyRepository.getShopAppliesByShopName(shopName, pageNum*pageSize, (pageNum+1)*pageSize));
    }
}
