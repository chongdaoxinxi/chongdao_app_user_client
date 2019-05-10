package com.chongdao.client.service.iml;

import com.chongdao.client.entitys.PackageUser;
import com.chongdao.client.repository.PackageUserRepository;
import com.chongdao.client.service.PackageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description 礼包用户关联表service
 * @Author onlineS
 * @Date 2019/5/9
 * @Version 1.0
 **/
@Service
public class PackageUserServiceImpl implements PackageUserService {
    @Autowired
    private PackageUserRepository packageUserRepository;

    @Override
    public void savePackageUserService(PackageUser pu) {
        Optional.ofNullable(pu).map(puI -> packageUserRepository.saveAndFlush(puI));
    }
}
