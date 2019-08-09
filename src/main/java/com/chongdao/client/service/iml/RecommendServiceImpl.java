package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.RecommendTypeEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.RecommendService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.ShareCodeUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.chongdao.client.vo.UserRecommendVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/9
 * @Version 1.0
 **/
@Service
public class RecommendServiceImpl implements RecommendService {
    private final static String RECOMMEND_URL = "https://xxx.xxxx.xxx";//推广H5页面地址

    @Autowired
    private RecommendInfoRepository recommendInfoRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecommendRecordRepository recommendRecordRepository;
    @Autowired
    private UserTransRepository userTransRepository;
    @Autowired
    private ExpressRepository expressRepository;
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public ResultResponse initRecommendUrl(String role, Integer id) {
        // 推广员类型, 推广员id
        Integer type = getRecommendType(role);
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        String url = RECOMMEND_URL + "type=" + type + "&recommendId=" + id;
        RecommendInfo ri = new RecommendInfo();
        ri.setRecommendCode(ShareCodeUtil.genShareCode(id, type));
        ri.setRecommenderId(id);
        ri.setType(type);
        ri.setRecommendUrl(url);
        ri.setCreateTime(new Date());
        recommendInfoRepository.saveAndFlush(ri);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), url);
    }

    /**
     * 获取角色对应的recommendType
     *
     * @param role
     * @return
     */
    private Integer getRecommendType(String role) {
        if (role.equals(RoleEnum.USER.getCode())) {
            return RecommendTypeEnum.USER_ROLE.getCode();//用户
        } else if (role.equals(RoleEnum.EXPRESS.getCode())) {
            return RecommendTypeEnum.EXPRESS_ROLE.getCode();//配送员
        } else if (role.equals(RoleEnum.SHOP_APP.getCode())) {
            return RecommendTypeEnum.EXPRESS_ROLE.getCode();//商家
        }
        return null;
    }

    @Override
    public ResultResponse getMyShareInfo(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer type = getRecommendType(tokenVo.getRole());
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        List<RecommendInfo> infos = recommendInfoRepository.findByRecommenderIdAndType(tokenVo.getUserId(), type);
        if (infos != null) {
            if (infos.size() == 1) {
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), infos.get(0));
            } else if (infos.size() > 1) {
                ResultResponse.createByErrorMessage("查询到该账号下存在多条推广人数据, 请联系管理员确认修改!");
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 被推广用户首单返利逻辑
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse recommendUserFirstOrder(Integer orderId) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
        if (orderInfo == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        //生成推广记录
        Integer userId = orderInfo.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }

        if(user.getIsLoginApp() == -1) {
            return ResultResponse.createByErrorMessage("该用户还未登录过APP, 无法进行返利!");
            //TODO 将这种用户保存在一个表里, 每天晚上进行定时任务查询, 只要用户后面登录了APP就再将返利给他
        }

        RecommendRecord rr = new RecommendRecord();
        rr.setUserId(orderInfo.getUserId());
        rr.setRecommenderId(user.getRecommendId());
        rr.setRecommendType(user.getRecommendType());
        rr.setConsumeType(RecommendTypeEnum.ORDER.getCode());
        rr.setConsumeId(orderId);
        BigDecimal payment = orderInfo.getPayment();
        rr.setConsumeMoney(payment);
        Integer percent = RecommendTypeEnum.ORDER_REWARD_PERCENT.getCode();
        rr.setRewardPercent(percent);
        //订单返现有最大值(现为58)
        BigDecimal multiplyReward = payment.multiply(new BigDecimal(String.valueOf(percent / 100)));
        if(multiplyReward.compareTo(new BigDecimal(String.valueOf(RecommendTypeEnum.ORDER_REWARD_MAX.getCode()))) > 1) {
            multiplyReward = new BigDecimal(String.valueOf(RecommendTypeEnum.ORDER_REWARD_MAX.getCode()));
        }
        rr.setRewardMoney(multiplyReward);
        rr.setIsRefund(-1);
        rr.setCreateTime(new Date());
        RecommendRecord recommendRecord = recommendRecordRepository.saveAndFlush(rr);

        //将奖金存入对应的账户
        boolean flag = storeReward(recommendRecord.getRecommenderId(), recommendRecord.getRecommendType(), recommendRecord.getConsumeType(), recommendRecord.getRewardMoney());
        if(flag) {
            return ResultResponse.createBySuccessMessage("存入资金成功");
        } else {
            //打印错误日志 TODO
            return ResultResponse.createByErrorMessage("存入资金失败!");
        }
    }

    /**
     * 转钱
     * @param recommendId
     * @param recommendType
     * @param consumeType
     * @param reward
     * @return
     */
    private boolean storeReward(Integer recommendId, Integer recommendType, Integer consumeType, BigDecimal reward) {
        if (recommendType == RecommendTypeEnum.USER_ROLE.getCode()) {
            //用户
            storeRewardUser(recommendId, consumeType, getConsumeTypeName(consumeType), reward);
        } else if (recommendType == RecommendTypeEnum.EXPRESS_ROLE.getCode()) {
            //配送员
            storeRewardExpress(recommendId, reward);
        } else if (recommendType == RecommendTypeEnum.SHOP_ROLE.getCode()) {
            //商家
            storeRewardShop(recommendId, consumeType, reward);
        }
        return false;
    }

    private String getConsumeTypeName(Integer consumeType) {
        if (consumeType == RecommendTypeEnum.ORDER.getCode()) {
            //用户推广 新用户首单
           return RecommendTypeEnum.ORDER.getName();
        } else if (consumeType == RecommendTypeEnum.MEDICAL_INSURANCE.getCode()) {
            //用户推广 医疗险
            return RecommendTypeEnum.MEDICAL_INSURANCE.getName();
        } else if (consumeType == RecommendTypeEnum.FAMILY_INSURANCE.getCode()) {
            //用户推广 家责险
            return RecommendTypeEnum.FAMILY_INSURANCE.getName();
        }
        return null;
    }

    private void storeRewardUser(Integer id, Integer consumeType, String recommendTypeName, BigDecimal reward) {
        //转钱
        User user = userRepository.findById(id).orElse(null);
        BigDecimal money = user.getMoney();
        if(money == null) {
            money = new BigDecimal(0.00);
        }
        user.setMoney(money.add(reward));
        userRepository.saveAndFlush(user);
        //生成记录
        UserTrans ut = new UserTrans();
        ut.setUserId(id);
        ut.setMoney(reward);
        if(reward.compareTo(new BigDecimal("0")) > 1) {
            //存钱
            ut.setComment(recommendTypeName);
        } else {
            //扣钱
            ut.setComment(recommendTypeName + "退款资金扣除");
        }
        ut.setType(consumeType + 3);//因为1,2, 3已经被使用过了
        ut.setCreateTime(new Date());
        userTransRepository.saveAndFlush(ut);
    }

    private void storeRewardExpress(Integer id, BigDecimal reward) {
        Express express = expressRepository.findById(id).orElse(null);
        if(express != null) {
            BigDecimal money = express.getMoney();
            if(money == null) {
                money = new BigDecimal("0.00");
            }
            express.setMoney(money.add(reward));
            expressRepository.saveAndFlush(express);
        }
    }

    private void storeRewardShop(Integer id, Integer consumeType, BigDecimal reward) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if(shop != null) {
            BigDecimal money = new BigDecimal("0.00");
            if(consumeType.equals(RecommendTypeEnum.ORDER.getCode())) {
                //首单
                BigDecimal recommendMoney = shop.getRecommendMoney();
                if(recommendMoney == null) {
                    recommendMoney = new BigDecimal("0.00");
                }
                money = money.add(recommendMoney);
                shop.setRecommendMoney(money.add(reward));
                shopRepository.saveAndFlush(shop);
            }else if(consumeType.equals(RecommendTypeEnum.MEDICAL_INSURANCE.getCode()) || consumeType.equals(RecommendTypeEnum.FAMILY_INSURANCE.getCode())) {
                //保险
                BigDecimal insuranceMoney = shop.getInsuranceMoney();
                if(insuranceMoney == null) {
                    insuranceMoney = new BigDecimal("0.00");
                }
                money = money.add(insuranceMoney);
                shop.setInsuranceMoney(money.add(reward));
                shopRepository.saveAndFlush(shop);
            }

        }
    }

    /**
     * 订单退款, 扣除奖金
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse refundOrderDeductReward(Integer orderId) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
        if (orderInfo == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        List<RecommendRecord> rrs = recommendRecordRepository.findByUserIdAndConsumeId(orderInfo.getUserId(), orderInfo.getId());
        if(rrs.size() == 1) {
            //更新RecommendRecord状态
            RecommendRecord rr = rrs.get(0);
            rr.setIsRefund(1);
            RecommendRecord recommendRecord = recommendRecordRepository.saveAndFlush(rr);
            //扣钱
            BigDecimal rewardMoney = recommendRecord.getRewardMoney();
            boolean flag = storeReward(recommendRecord.getRecommenderId(), recommendRecord.getRecommendType(), recommendRecord.getConsumeType(), rewardMoney.multiply(new BigDecimal("-1")));
            if(flag) {
                return ResultResponse.createBySuccessMessage("扣除资金成功");
            } else {
                //打印错误日志 TODO
                return ResultResponse.createByErrorMessage("扣除资金失败!");
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    @Override
    public ResultResponse firstLoginAppCheck(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        User user = userRepository.findById(tokenVo.getUserId()).orElse(null);
        if(user != null) {
            Integer isLoginApp = user.getIsLoginApp();
            if(isLoginApp == -1) {
                user.setIsLoginApp(1);
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userRepository.saveAndFlush(user));
            } else {
                return ResultResponse.createBySuccessMessage("该用户已登录过APP, 无需更新状态!");
            }
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse getMyRecommendRecordData(String token, Integer consumeType) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer recommendType = getRecommendType(tokenVo.getRole());
        List<RecommendRecord> list = recommendRecordRepository.findByRecommenderIdAndRecommendTypeAndConsumeType(tokenVo.getUserId(), recommendType, consumeType);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), list);
    }

    @Override
    public ResultResponse getMyRecommendUserData(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer recommendType = getRecommendType(tokenVo.getRole());
        List<User> list = userRepository.findByRecommendIdAndRecommendType(tokenVo.getUserId(), recommendType);
        List<UserRecommendVO> resp = new ArrayList<>();
        for (User user : list) {
            UserRecommendVO urv = new UserRecommendVO();
            BeanUtils.copyProperties(user, urv);
            resp.add(urv);
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), resp);
    }
}
