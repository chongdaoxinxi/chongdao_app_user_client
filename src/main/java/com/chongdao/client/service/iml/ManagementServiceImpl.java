package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Area;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.AreaRepository;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.service.ManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/3
 * @Version 1.0
 **/
@Service
public class ManagementServiceImpl implements ManagementService {
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public ResultResponse getManagementById(Integer id) {
        Management management = managementRepository.findById(id).orElse(null);
        if(management != null) {
            String areaCode = management.getAreaCode();
            if(StringUtils.isNotBlank(areaCode)) {
                List<Area> areas = areaRepository.findByCodeAndLevel(areaCode, 1).orElse(null);
                if(areas != null && areas.size() > 0) {
                    Area area = areas.get(0);
                    management.setAreaName(area.getName());
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), management);
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }
}
