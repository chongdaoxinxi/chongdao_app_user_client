package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.ModuleRepository;
import com.chongdao.client.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/14
 * @Version 1.0
 **/
@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public ResultResponse getModuleData() {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), moduleRepository.findByStatus(1).orElse(null));
    }
}
