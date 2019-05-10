package com.chongdao.client.service.iml;

import com.chongdao.client.entitys.PackageCard;
import com.chongdao.client.repository.PackageCardRepository;
import com.chongdao.client.service.PackageCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description 礼包优惠券关联表service
 * @Author onlineS
 * @Date 2019/5/9
 * @Version 1.0
 **/
@Service
public class PackageCardServiceImpl implements PackageCardService {
    @Autowired
    private PackageCardRepository packageCardRepository;

    @Override
    public void savePackageCard(PackageCard pc) {
        Optional.ofNullable(pc).map(pcI -> packageCardRepository.saveAndFlush(pcI));
    }
}
