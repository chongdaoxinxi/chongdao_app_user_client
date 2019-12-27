package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.ShopSignInfo;
import com.chongdao.client.repository.ShopSignInfoRepository;
import com.chongdao.client.service.ShopSignService;
import com.chongdao.client.vo.ShopSignTypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/27
 * @Version 1.0
 **/
@Service
public class ShopSignServiceImpl implements ShopSignService {
    @Autowired
    private ShopSignInfoRepository shopSignInfoRepository;

    /**
     * 申请商家入驻
     *
     * @param shopSignInfo
     * @return
     */
    @Override
    public ResultResponse applyShopSign(ShopSignInfo shopSignInfo) {
        ShopSignInfo ssi = new ShopSignInfo();
        BeanUtils.copyProperties(shopSignInfo, ssi);
        if(ssi.getId() == null) {
            ssi.setCreateTime(new Date());
        } else {
            ssi.setUpdateTime(new Date());
        }
        ssi.setStatus(0);//待审核
        return ResultResponse.createBySuccess(shopSignInfoRepository.save(ssi));
    }

    @Override
    public ResultResponse getShopType() {
        List<ShopSignTypeVO> list = new ArrayList<>();
        list.add(new ShopSignTypeVO(0, "普通宠物店"));
        list.add(new ShopSignTypeVO(4, "活体宠物店"));
        list.add(new ShopSignTypeVO(2, "普通宠物医院"));
        list.add(new ShopSignTypeVO(3, "宠物医保宠物医院"));
        return ResultResponse.createBySuccess(list);
    }
}
