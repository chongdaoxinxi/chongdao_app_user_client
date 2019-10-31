package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Express;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.mapper.ExpressMapper;
import com.chongdao.client.repository.ExpressRepository;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.service.ExpressService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.MD5Util;
import com.chongdao.client.vo.ExpressVO;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/15
 * @Version 1.0
 **/
@Service
public class ExpressServiceImpl implements ExpressService {
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private ExpressMapper expressMapper;
    @Autowired
    private ExpressRepository expressRepository;

    @Override
    public ResultResponse getExpressList(String token, String expressName, Integer selectType, Integer selectStatus, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (role != null && role.equals(RoleEnum.ADMIN_PC.getCode())) {
            Management management = managementRepository.findById(tokenVo.getUserId()).orElse(null);
            if(management != null) {
                if(expressName == null) {
                    expressName = "";
                }
                PageHelper.startPage(pageNum, pageSize);
                List<ExpressVO> list = expressMapper.getExpressListByAreaCodeAndName(management.getAreaCode(), expressName, selectType, selectStatus);
                PageInfo pageResult = new PageInfo(list);
                pageResult.setList(list);
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), pageResult);
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    @Override
    public ResultResponse saveExpress(Express express) {
        Integer id = express.getId();
        if(id == null) {
            Express newExp = new Express();
            BeanUtils.copyProperties(express, newExp);
            newExp.setUsername(newExp.getPhone());
            newExp.setPassword(MD5Util.MD5(newExp.getNativePassword()));
            newExp.setCreateTime(new Date());
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), expressRepository.saveAndFlush(newExp));
        } else {
            express.setUsername(express.getPhone());
            express.setPassword(MD5Util.MD5(express.getNativePassword()));
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), expressRepository.saveAndFlush(express));
        }
    }

    @Override
    public ResultResponse removeExpress(Integer expressId) {
        Express express = expressRepository.findById(expressId).orElse(null);
        if(express != null) {
            expressRepository.delete(express);
            return ResultResponse.createBySuccess();
        }
        return ResultResponse.createByErrorMessage("删除失败!");
    }

    @Override
    public ResultResponse getExpressInfo(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return ResultResponse.createBySuccess(expressRepository.findByIdAndStatus(tokenVo.getUserId(), 1));
    }
}
