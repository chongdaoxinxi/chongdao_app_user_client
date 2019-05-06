package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Package;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.PackageRepository;
import com.chongdao.client.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 大礼包
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Override
    public ResultResponse<List<Package>> getPackageList() {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), packageRepository.findAll());
    }
}
