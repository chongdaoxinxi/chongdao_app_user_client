package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.AdminStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.AdminService;
import com.chongdao.client.utils.MD5Util;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.AdminInfoVO;
import com.chongdao.client.vo.AdminLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public ResultResponse adminLogin(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_NAME_OR_PASSWORD_EMPTY.getStatus(), AdminStatusEnum.ADMIN_NAME_OR_PASSWORD_EMPTY.getMessage());
        }
        List<Management> managementList = managementRepository.findByNameAndPasswordAndStatus(username, MD5Util.MD5(password), 1).orElse(null);
        if(managementList != null && managementList.size() > 0) {
            if (managementList.size() == 1) {
                return assembleAdminLogin(managementList.get(0));
            } else {
                return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_DATA_ERROR.getStatus(), AdminStatusEnum.ADMIN_DATA_ERROR.getMessage());
            }
        } else {
            List<Shop> shopList = shopRepository.findByAccountNameAndPassword(username, MD5Util.MD5(password)).orElse(null);
            if(shopList != null && shopList.size() > 0) {
                if(shopList.size() == 1) {
                    return assembleShopLogin(shopList.get(0));
                } else {
                    return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_DATA_ERROR.getStatus(), AdminStatusEnum.ADMIN_DATA_ERROR.getMessage());
                }
            }
            return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_ERROR_PASSWORD.getStatus(), AdminStatusEnum.ADMIN_ERROR_PASSWORD.getMessage());
        }
    }

    private ResultResponse assembleAdminLogin(Management management) {
        Integer id = management.getId();
        String username = management.getName();
        String password = management.getPassword();
        if(id != null && username != null && password != null) {
            AdminLoginVO adminLoginVO = new AdminLoginVO();
            adminLoginVO.setManagementId(id);
            adminLoginVO.setUsername(username);
            adminLoginVO.setPassword(password);
            Date date = new Date();
            adminLoginVO.setLastLoginTime(date);
            String role = RoleEnum.ADMIN_PC.getCode();
            if(management.getType() == 1) {
                role = RoleEnum.SUPER_ADMIN_PC.getCode();
            } else if(management.getType() == 3) {
                role = RoleEnum.INSURANCE_PC.getCode();
            }
            adminLoginVO.setToken(TokenUtil.generateToken(id, username, date, role));
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), adminLoginVO);
        } else {
            return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_DATA_ERROR.getStatus(), AdminStatusEnum.ADMIN_DATA_ERROR.getMessage());
        }
    }

    private ResultResponse assembleShopLogin(Shop shop) {
        Integer id = shop.getId();
        String username = shop.getShopName();
        String password = shop.getPassword();
        if(id != null && username != null && password != null) {
            AdminLoginVO adminLoginVO = new AdminLoginVO();
            adminLoginVO.setManagementId(id);
            adminLoginVO.setUsername(username);
            adminLoginVO.setPassword(password);
            Date date = new Date();
            adminLoginVO.setLastLoginTime(date);
            adminLoginVO.setToken(TokenUtil.generateToken(id, username, date, RoleEnum.SHOP_PC.getCode()));
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), adminLoginVO);
        } else {
            return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_DATA_ERROR.getStatus(), AdminStatusEnum.ADMIN_DATA_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse getAdminInfo(Integer managementId, String role) {
        if(role != null ) {
            if (role.equals(RoleEnum.ADMIN_PC.getCode()) || role.equals(RoleEnum.SUPER_ADMIN_PC.getCode()) || role.equals(RoleEnum.INSURANCE_PC.getCode())) {
                Management management = managementRepository.findById(managementId).orElse(null);
                if (management != null) {
                    AdminInfoVO vo = new AdminInfoVO();
                    vo.setName(management.getName());
                    vo.setUserId(management.getId());
                    if (!StringUtils.isBlank(management.getIcon())) {
                        vo.setAvatar(management.getIcon());
                    }
                    if (role.equals(RoleEnum.ADMIN_PC.getCode())) {
                        String[] arr = {"admin"};
                        vo.setAccess(arr);
                    } else if (role.equals(RoleEnum.SUPER_ADMIN_PC.getCode())) {
                        String[] arr = {"superadmin"};
                        vo.setAccess(arr);
                    } else if(role.equals(RoleEnum.INSURANCE_PC.getCode())) {
                        String[] arr = {"insuranceadmin"};
                        vo.setAccess(arr);
                    }
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), vo);
                }
            } else if (role.equals("SHOP_PC")) {
                Shop shop = shopRepository.findById(managementId).orElse(null);
                if (shop != null) {
                    AdminInfoVO vo = new AdminInfoVO();
                    vo.setName(shop.getShopName());
                    vo.setAvatar(shop.getLogo());
                    vo.setUserId(shop.getId());
                    String[] arr = new String[5];
                    arr[0] = "shop";
                    if(shop.getType() == 2) {
                        //医院类店铺
                        arr[1] = "medical";
                    }
                    vo.setAccess(arr);
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), vo);
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(AdminStatusEnum.ADMIN_DATA_ERROR.getStatus(), AdminStatusEnum.ADMIN_DATA_ERROR.getMessage());
    }
}
