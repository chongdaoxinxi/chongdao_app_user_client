package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Unit;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.GoodsTypeMapper;
import com.chongdao.client.repository.UnitRepository;
import com.chongdao.client.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/17
 * @Version 1.0
 **/
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public ResultResponse<List<Unit>> getUnitList(Integer moduleId, Integer categoryId) {
        //商品 直接取商品的规格单位

        //服务 传入categoryId取, 返回值为空List说明没有匹配的即不需要规格单位, 前端隐藏
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), unitRepository.findAll());
    }
}
