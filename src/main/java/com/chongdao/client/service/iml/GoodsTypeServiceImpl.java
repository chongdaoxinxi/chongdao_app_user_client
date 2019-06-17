package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.GoodsTypeMapper;
import com.chongdao.client.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/14
 * @Version 1.0
 **/
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public ResultResponse addGoodsType(GoodsType goodsType) {
        goodsType.setCreateTime(new Date());
        goodsType.setUpdateTime(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), goodsTypeMapper.insert(goodsType));
    }
}
