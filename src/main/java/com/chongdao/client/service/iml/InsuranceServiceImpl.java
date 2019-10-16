package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.InsuranceClaimsMapper;
import com.chongdao.client.mapper.InsuranceFeeRecordMapper;
import com.chongdao.client.mapper.InsuranceOrderMapper;
import com.chongdao.client.mapper.InsuranceShopChipMapper;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.InsuranceService;
import com.chongdao.client.utils.InsuranceUUIDUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.chongdao.client.vo.UserInsuranceTodoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/2
 * @Version 1.0
 **/
@Service
public class InsuranceServiceImpl implements InsuranceService {
    @Value("${insurance.zcgName}")
    private String zcgName;//运输险固定投保人/被保人姓名
    @Value("${insurance.zcgCardNo}")
    private String zcgCardNo;//运输险固定投保人/被保人身份证号
    @Value("${insurance.zcgPhone}")
    private String zcgPhone;//运输险固定投保人/被保人电话
    @Value("${insurance.zcgMail}")
    private String zcgMail;//运输险固定投保人/被保人邮箱
    @Value("${insurance.zcgAddress}")
    private String zcgAddress;//运输险固定投保人/被保人地址
    @Value("${insurance.zcgSustainTime}")
    private Integer zcgSustainTime;//运输险自动往后顺延12小时
    @Value("${insurance.zcgRationType}")
    private String zcgRationType;//运输险代码
    @Value("${insurance.zcgSumAmount}")
    private String zcgSumAmount;//运输险代码
    @Value("${insurance.zcgSumPremium}")
    private String zcgSumPremium;//运输险代码

    @Autowired
    private InsuranceExternalService insuranceExternalService;
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;
    @Autowired
    private InsuranceOrderMapper insuranceOrderMapper;
    @Autowired
    private InsuranceOrderAuditRepository insuranceOrderAuditRepository;
    @Autowired
    private InsuranceShopChipRepository insuranceShopChipRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InsuranceClaimsMapper insuranceClaimsMapper;
    @Autowired
    private InsuranceShopChipMapper insuranceShopChipMapper;
    @Autowired
    private InsuranceFeeRecordMapper insuranceFeeRecordMapper;

    /**
     * 保存保单数据
     *
     * @return
     */
    @Override
    @Transactional
    public ResultResponse saveInsurance(InsuranceOrder insuranceOrder) throws IOException {
        //先将数据保存在我们数据库
        InsuranceOrder order = new InsuranceOrder();
        Integer beneficiary = insuranceOrder.getBeneficiary();

        Integer insuranceType = insuranceOrder.getInsuranceType();
        if (insuranceType != null && (insuranceType == 1 || insuranceType == 2)) {
            //基础校验
            if (beneficiary == null) {
                return ResultResponse.createByErrorMessage("被保人与投保人关系不能为空!");
            }
            if (beneficiary != null && beneficiary != 0) {
                if (insuranceOrder.getAcceptName() == null || insuranceOrder.getAcceptPhone() == null || insuranceOrder.getAcceptCardNo() == null || insuranceOrder.getAcceptAddress() == null || insuranceOrder.getAcceptCardType() == null) {
                    return ResultResponse.createByErrorMessage("被保人必填信息(身份证类型/身份证号/被保人名称/被保人号码/被保人地址)不完整!");
                }
            }
            BeanUtils.copyProperties(insuranceOrder, order);
            if (order.getId() == null) {
                //新订单, 新生成的数据
                //生成订单号
                order.setInsuranceOrderNo(InsuranceUUIDUtil.generateUUID());//订单号设置为保险投保接口所需要的UUID

                //如果投保人与被保人关系为本人时, 复制投保人信息至被保人
                if ((order.getInsuranceType() != null && order.getInsuranceType() != 2) || (order.getBeneficiary() != null && order.getBeneficiary() == 0)) {
                    //非家责险或者被保人与投保人关系为别人
                    order.setAcceptName(order.getName());
                    order.setAcceptPhone(order.getPhone());
                    if (order.getEmail() != null) {
                        order.setAcceptMail(order.getEmail());
                    }
                    if (order.getAddress() != null) {
                        order.setAcceptAddress(order.getAddress());
                    }
                    order.setAcceptCardType(order.getCardType());
                    order.setAcceptCardNo(order.getCardNo());
                }

                //校验宠物芯片是否被使用
                Integer medicalInsuranceShopChipId = order.getMedicalInsuranceShopChipId();
                if (medicalInsuranceShopChipId != null) {
                    InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(medicalInsuranceShopChipId).orElse(null);
                    if (insuranceShopChip == null) {
                        return ResultResponse.createByErrorMessage("无效的宠物芯片, 请重新选择!");
                    } else {
                        if (insuranceShopChip.getStatus() != 1) {
                            return ResultResponse.createByErrorMessage("所选宠物芯片已被使用, 请重新选择!");
                        } else {
                            insuranceShopChip.setStatus(0);
                            insuranceShopChip.setSelectedTime(new Date());//更新被选中时间
                            insuranceShopChipRepository.save(insuranceShopChip);//更新所选宠物芯片的状态
                        }
                    }
                }

                //校验宠物芯片代码是否存在重复
                String shopChipCode = order.getShopChipCode();
                if (StringUtils.isNotBlank(shopChipCode)) {
                    Integer amount = insuranceOrderMapper.checkShopChipIsUsed(shopChipCode);
                    if (amount != null && amount > 0) {
                        return ResultResponse.createByErrorMessage("已存在相同的宠物芯片代码, 请检查宠物芯片代码是否正确!");
                    }
                }
            }
        }

        //设置一些默认参数
        setDefaultInsuranceOrderParam(order);
        InsuranceOrder savedOrder = insuranceOrderRepository.save(order);
        //如果要加入审核机制, 那么这里需要写一些处理逻辑, 区分是保存订单还是付款后的请求外部接口生成订单

        //请求外部接口, 生成保单
        return insuranceExternalService.generateInsure(savedOrder);
    }

    @Override
    public ResultResponse insuranceZcg(OrderInfo orderInfo) throws IOException {
        Integer insuranceCount = orderInfo.getPetCount();//根据宠物数量来投保
        for(int i = 0; i < insuranceCount; i++) {
            InsuranceOrder order = setZcgInsuranceOrderParam();
            InsuranceOrder savedOrder = insuranceOrderRepository.save(order);
            //如果要加入审核机制, 那么这里需要写一些处理逻辑, 区分是保存订单还是付款后的请求外部接口生成订单
            //请求外部接口, 生成保单
            insuranceExternalService.generateInsure(savedOrder);
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 设置运输险订单参数
     */
    private InsuranceOrder setZcgInsuranceOrderParam() {
        InsuranceOrder order = new InsuranceOrder();
        //生成订单号
        order.setInsuranceOrderNo(InsuranceUUIDUtil.generateUUID());//订单号设置为保险投保接口所需要的UUID
        //设置保险类型为运输险
        order.setInsuranceType(3);
        //根据配送订单号保存相关的保险订单信息(用户信息及保险时间信息等)
        order.setBeneficiary(0);
        order.setName(zcgName);
        order.setPhone(zcgPhone);
        order.setCardType("01");//默认类型为身份证
        order.setCardNo(zcgCardNo);//身份证号userShareCallBack
        order.setEmail(zcgMail);
        order.setAddress(zcgAddress);//地址
        //被保人与投保人相同, 即为下单人
        order.setAcceptName(zcgName);
        order.setAcceptPhone(zcgPhone);
        order.setAcceptCardType("01");
        order.setAcceptCardNo(zcgCardNo);
        order.setAddress(zcgMail);
        order.setInsuranceEffectTime(new Date());//保单开始时间
        order.setInsuranceFailureTime(new Date(System.currentTimeMillis() + 60 * 1000 * 60 * zcgSustainTime));//保单结束时间

        //存入配送险相关信息
        order.setRationType(zcgRationType);
        order.setSumAmount(new BigDecimal(zcgSumAmount));//保额
        order.setSumPremium(new BigDecimal(zcgSumPremium));//保费

        //设置一些默认参数
        setDefaultInsuranceOrderParam(order);
        return order;
    }

    /**
     * 设置一些默认参数
     * @param order
     */
    private void setDefaultInsuranceOrderParam(InsuranceOrder order) {
        //设置一些默认参数
        order.setAcceptSeqNo(1);//默认被保人只有1个
        order.setIsSendMsg(1);//默认发送短消息
        order.setStatus(0);//状态初始化为已保存
        order.setCreateTime(new Date());
    }

    @Override
    public ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer userId = tokenVo.getUserId();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderRepository.findByUserId(userId, pageable));
    }

    @Override
    public ResultResponse getInsuranceDetail(Integer insuranceId) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderMapper.getInsuranceOrderDetail(insuranceId));
    }

    @Override
    public ResultResponse downloadElectronicInsurancePolicy(Integer insuranceId) {
        return null;
    }

    @Override
    public ResultResponse getInsuranceDataList(String token, Integer insuranceType, String userName, String phone, String insuranceOrderNo, Date start, Date end, Integer status, Integer pageNum, Integer pageSize) {
        if(status != null && status == 99) {
            status = null;
        }
        PageHelper.startPage(pageNum, pageSize);
        //根据token, 加入地区areaCode限制

        List<InsuranceOrder> insuranceDataList = insuranceOrderMapper.getInsuranceDataList(insuranceType, userName, phone, insuranceOrderNo, start, end, status);
        PageInfo pageResult = new PageInfo(insuranceDataList);
        pageResult.setList(insuranceDataList);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    @Transactional
    public ResultResponse auditInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        updateInsuranceOrderStatus(token, insuranceOrderId, targetStatus, note);
        return ResultResponse.createBySuccessMessage("审核通过成功!");
    }

    @Override
    @Transactional
    public ResultResponse refuseInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        updateInsuranceOrderStatus(token, insuranceOrderId, targetStatus, note);
        return ResultResponse.createBySuccessMessage("拒绝审核成功!");
    }

    @Override
    public ResultResponse pollingCheckOrderStatus(Integer insuranceOrderId) {
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        if (insuranceOrder == null) {
            return ResultResponse.createByErrorMessage("无效的订单ID!");
        }
        Integer status = insuranceOrder.getStatus();
        if (status != null && status == 1) {
            Date applyTime = insuranceOrder.getApplyTime();
            if (applyTime != null) {
                Date now = new Date();
                long l = now.getTime() - applyTime.getTime();
                double r = l * 1.0 / (1000 * 60 * 60);
                if (r > 24) {
                    return ResultResponse.createByErrorMessage("支付有效期已过!");
                }
            } else {
                return ResultResponse.createByErrorMessage("没有找到有效的预下单时间!");
            }
        } else {
            return ResultResponse.createByErrorMessage("无效的订单状态!");
        }
        return ResultResponse.createBySuccess(true);
    }

    @Override
    public ResultResponse getInsuranceUserTodo(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer userId = tokenVo.getUserId();
        if(userId != null) {
            Map resp = new HashMap<>();
            List<UserInsuranceTodoVO> claimsTodo = getClaimsTodo(userId);
            List<UserInsuranceTodoVO> chipTodo = getChipTodo(userId);
            List<UserInsuranceTodoVO> insuraneFeeTodo = getInsuraneFeeTodo(userId);
            if(claimsTodo != null && claimsTodo.size() == 0 && chipTodo != null && chipTodo.size() == 0 && insuraneFeeTodo != null && insuraneFeeTodo.size() == 0) {
                return ResultResponse.createBySuccessMessage("没有待办!");
            }
            //理赔金额待办
            resp.put("claims", getClaimsTodo(userId));
            //宠物芯片核销待办
            resp.put("chip", getChipTodo(userId));
            //医疗费用待办
            resp.put("feeRecord", getInsuraneFeeTodo(userId));
            return ResultResponse.createBySuccess(resp);
        } else {
            return ResultResponse.createByErrorMessage("无效的token");
        }
    }

    /**
     * 理赔金额待办
     */
    private List<UserInsuranceTodoVO>  getClaimsTodo(Integer userId) {
        return insuranceClaimsMapper.getUserTodoList(userId);
    }

    /**
     * 宠物芯片核销待办
     */
    private List<UserInsuranceTodoVO> getChipTodo(Integer userId) {
        return insuranceShopChipMapper.getUserTodoList(userId);
    }

    /**
     * 医疗费用待办
     */
    private List<UserInsuranceTodoVO> getInsuraneFeeTodo(Integer userId) {
        return insuranceFeeRecordMapper.getUserTodoList(userId);
    }

    private void updateInsuranceOrderStatus(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        insuranceOrder.setStatus(targetStatus);
        insuranceOrderRepository.save(insuranceOrder);
        //生成审核记录
        Integer oldStatus = insuranceOrder.getStatus();
        InsuranceOrderAudit insuranceOrderAudit = new InsuranceOrderAudit();
        insuranceOrderAudit.setInsuranceOrderId(insuranceOrderId);
        insuranceOrderAudit.setOldStatus(oldStatus);
        insuranceOrderAudit.setNewStatus(targetStatus);
        insuranceOrderAudit.setNote(note);
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        insuranceOrderAudit.setManagementId(tokenVo.getUserId());
        insuranceOrderAuditRepository.save(insuranceOrderAudit);
    }
}
