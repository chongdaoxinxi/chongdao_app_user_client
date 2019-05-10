package com.chongdao.client.service;

import com.chongdao.client.entitys.PackageCard;

import java.util.List;

public interface PackageCardService {
    void savePackageCard(PackageCard pc);

    List<PackageCard> getPackageCardById(Integer packageId);
}
