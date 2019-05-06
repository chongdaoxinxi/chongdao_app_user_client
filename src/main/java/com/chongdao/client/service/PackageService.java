package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Package;

import java.util.List;

/**
 * 礼包
 */
public interface PackageService {
    ResultResponse<List<Package>> getPackageList();
}
