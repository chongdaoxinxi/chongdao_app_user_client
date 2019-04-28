package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Banner;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.BannerRepository;
import com.chongdao.client.service.BannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 轮播图
 * @Author onlineS
 * @Date 2019/4/23
 * @Version 1.0
 **/
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 根据区域码获取轮播图
     * @param areaCode
     * @return
     */
    @Override
    public ResultResponse<List<Banner>> getBannerByAreaCode(String areaCode) {
        if(StringUtils.isBlank(areaCode)) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        List<Banner> banners = bannerRepository.findByAreaCode(areaCode);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), banners);
    }
}
