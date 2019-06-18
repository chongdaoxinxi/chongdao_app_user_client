package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Module;
import com.chongdao.client.entitys.Unit;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.GoodsTypeMapper;
import com.chongdao.client.repository.ModuleRepository;
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
    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public ResultResponse<List<Unit>> getUnitList(Integer moduleId, Integer categoryId) {
        //商品 直接取商品的规格单位
        List<Module> modules = moduleRepository.findByIdAndStatus(moduleId, 1).orElse(null);
        if(modules != null && modules.size() > 0) {
            Module module = modules.get(0);
            Integer type = module.getType();
            if(type == 1) {
                //商品
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), unitRepository.findByType(type).orElse(null));
            } else if(type == 2) {
                //服务
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), unitRepository.getUnitFindInSetCategoryIdList(categoryId));
            } else {
                //商品分类数据有问题, 请检查
                return ResultResponse.createByErrorMessage("商品分类数据有问题, 请检查");
            }
        } else {
            //商品分类数据有问题, 请检查
            return ResultResponse.createByErrorMessage("商品分类数据有问题, 请检查");
        }
    }
}
