package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Area;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.AreaRepository;
import com.chongdao.client.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/21
 * @Version 1.0
 **/
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public ResultResponse getTopLevelAreaDataList(Integer level, Integer isOpen) {
        List<Area> areas = areaRepository.findByLevelAndIsOpen(level, isOpen).orElse(null);
        if(areas == null) {
            areas = new ArrayList<>();
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), areas);
    }

    @Override
    public ResultResponse getAreaDataByParentId(Integer level, Integer isOpen, Integer pid) {
        List<Area> areas = areaRepository.findByLevelAndIsOpenAndPid(level, isOpen, pid).orElse(null);
        if(areas == null) {
            areas = new ArrayList<>();
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), areas);
    }
}
