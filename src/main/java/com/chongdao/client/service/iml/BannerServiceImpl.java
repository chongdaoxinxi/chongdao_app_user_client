package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Banner;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.mapper.BannerMapper;
import com.chongdao.client.repository.BannerRepository;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.service.BannerService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private ManagementRepository managementRepository;

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

    @Override
    public ResultResponse getBannerList(String token, Integer status) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if(StringUtils.isNotBlank(role)) {
            if(role.equals(RoleEnum.ADMIN_PC.getCode())) {
                Management management = managementRepository.findById(tokenVo.getUserId()).orElse(null);
                if(management != null) {
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), bannerMapper.getBannerListByAreaCodeAndStatus(management.getAreaCode(), status));
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    @Override
    public ResultResponse saveBanner(Banner banner) {
        if(banner.getId() == null) {
            banner.setCreateTime(new Date());
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), bannerRepository.saveAndFlush(banner));
    }

    @Override
    public ResultResponse deleteBanner(Integer id) {
        Banner banner = bannerRepository.findById(id).orElse(null);
        if(banner != null) {
            bannerRepository.delete(banner);
            return ResultResponse.createBySuccessMessage("删除成功!");
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }
}
