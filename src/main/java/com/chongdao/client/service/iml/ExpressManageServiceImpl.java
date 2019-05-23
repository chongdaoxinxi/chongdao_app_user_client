package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Express;
import com.chongdao.client.enums.ManageStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.ExpressRepository;
import com.chongdao.client.service.ExpressManageService;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.ExpressLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @Description 配送端
 * @Author onlineS
 * @Date 2019/5/17
 * @Version 1.0
 **/
@Service
public class ExpressManageServiceImpl implements ExpressManageService {
    @Autowired
    private ExpressRepository expressRepository;

    @Override
    public ResultResponse expressLogin(String username, String password) {
        // 非空校验
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.SHOP_NAME_OR_PASSWORD_EMPTY.getStatus(), ManageStatusEnum.SHOP_NAME_OR_PASSWORD_EMPTY.getMessage());
        }
        //正确性校验
        Optional<Express> expressOp = expressRepository.findByUsernameAndPassword(username, password);
        if(expressOp.isPresent()) {
            Express express = expressOp.get();
            Integer status = express.getStatus();
            if(status == 1) {
                return assembleExpressLogin(express);
            } else {
                return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.ACCOUNT_FREEZE.getStatus(), ManageStatusEnum.ACCOUNT_FREEZE.getMessage());
            }
        } else {
            return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.SHOP_ERROR_PASSWORD.getStatus(), ManageStatusEnum.SHOP_ERROR_PASSWORD.getMessage());
        }
    }

    private ResultResponse assembleExpressLogin(Express express) {
        Integer id = express.getId();
        String username = express.getUsername();
        String password = express.getPassword();
        if(id != null && username != null && password != null) {
            ExpressLoginVO eVo = new ExpressLoginVO();
            eVo.setExpressId(id);
            eVo.setUsername(username);
            eVo.setPassword(password);
            Date date = new Date();
            eVo.setLastLoginTime(date);
            eVo.setToken(TokenUtil.generateToken(id, username, date));
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), eVo);
        } else {
            return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.ACCOUNT_INFO_ERROR.getStatus(), ManageStatusEnum.ACCOUNT_INFO_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse expressLogout(String username, String password) {
        return null;
    }
}
