package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.ExpressRule;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.repository.ExpressRuleRepository;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.service.ExpressRuleService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/15
 * @Version 1.0
 **/
@Service
public class ExpressRuleServiceImpl implements ExpressRuleService {
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private ExpressRuleRepository expressRuleRepository;

    /**
     * 获取配送规则(根据管理员token)
     * @param token
     * @return
     */
    @Override
    public ResultResponse getExpressRule(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (StringUtils.isNotBlank(role) && role.equals(RoleEnum.ADMIN_PC.getCode())) {
            String areaCode = getAreaCodeByManagementId(tokenVo.getUserId());
            if (StringUtils.isNotBlank(areaCode)) {
                ExpressRule expressRule = expressRuleRepository.findByAreaCode(areaCode).orElse(null);
                if (expressRule != null) {
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), expressRule);
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 根据管理员id获取管理员所属区域码
     * @param id
     * @return
     */
    private String getAreaCodeByManagementId(Integer id) {
        Management management = managementRepository.findById(id).orElse(null);
        if (management != null) {
            String areaCode = management.getAreaCode();
            if (StringUtils.isNotBlank(areaCode)) {
                return areaCode;
            }
        }
        return null;
    }

    /**
     * 保存配送规则
     * @param token
     * @param expressRule
     * @return
     */
    @Override
    public ResultResponse saveExpressRule(String token, ExpressRule expressRule) {
        ExpressRule newExp = new ExpressRule();
        BeanUtils.copyProperties(expressRule, newExp);
        Integer id = expressRule.getId();
        if(id == null) {
            //新建
            ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
            String role = tokenVo.getRole();
            if (StringUtils.isNotBlank(role) && role.equals(RoleEnum.ADMIN_PC.getCode())) {
                String areaCode = getAreaCodeByManagementId(tokenVo.getUserId());
                if(StringUtils.isNotBlank(areaCode)) {
                    newExp.setAreaCode(areaCode);
                    newExp.setCreateTime(new Date());
                }
            } else {
                return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
            }
        }
        newExp.setUpdateTime(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), expressRuleRepository.saveAndFlush(expressRule));
    }
}
