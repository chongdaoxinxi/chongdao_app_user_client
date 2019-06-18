package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;
import com.chongdao.client.entitys.Module;
import com.chongdao.client.entitys.Unit;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.GoodsTypeMapper;
import com.chongdao.client.repository.ModuleRepository;
import com.chongdao.client.repository.UnitRepository;
import com.chongdao.client.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private UnitRepository unitRepository;
    public static final Integer CatFoodGoodsTypeId = 1;
    public static final Integer DogFoodGoodsTypeId = 2;

    @Override
    public ResultResponse addGoodsType(GoodsType goodsType) {
        goodsType.setCreateTime(new Date());
        goodsType.setUpdateTime(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), goodsTypeMapper.insert(goodsType));
    }

    @Override
    public ResultResponse getSelectGoodsTypeSpecialConfig(Integer moduleId, Integer categoryId, Integer goodsTypeId) {
        Map resp = new HashMap();
        List<Module> modules = moduleRepository.findByIdAndStatus(moduleId, 1).orElse(null);
        if(modules != null && modules.size() > 0) {
            Module module = modules.get(0);
            Integer type = module.getType();
            if(type == 1) {
                //商品
                if(goodsTypeId == CatFoodGoodsTypeId) {
                    //是狗粮
                    resp.put("isCatFood", false);
                    resp.put("isDogFood", true);
                    resp.put("isAppointService", false);
                } else if (goodsTypeId == DogFoodGoodsTypeId) {
                    //是猫粮
                    resp.put("isCatFood", true);
                    resp.put("isDogFood", false);
                    resp.put("isAppointService", false);
                }
            } else if(type == 2) {
                //服务
                List<Unit> units = unitRepository.getUnitFindInSetCategoryIdList(categoryId);
                if(units != null && units.size() > 0) {
                    //是指定服务
                    resp.put("isCatFood", false);
                    resp.put("isDogFood", false);
                    resp.put("isAppointService", true);
                } else {
                    //不是指定服务
                    resp.put("isCatFood", false);
                    resp.put("isDogFood", false);
                    resp.put("isAppointService", false);
                }
            }
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), resp);
    }
}
