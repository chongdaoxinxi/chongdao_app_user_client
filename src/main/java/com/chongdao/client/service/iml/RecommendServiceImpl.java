package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.RecommendTypeEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.mapper.RecommendMapper;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.RecommendService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.QrCodeUtils;
import com.chongdao.client.utils.ShareCodeUtil;
import com.chongdao.client.vo.RecommendInfoVO;
import com.chongdao.client.vo.RecommendRecordVO;
import com.chongdao.client.vo.ResultTokenVo;
import com.chongdao.client.vo.UserRecommendVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/9
 * @Version 1.0
 **/
@Service
public class RecommendServiceImpl implements RecommendService {
    private final static String RECOMMEND_URL = "https://xxx.xxxx.xxx";//推广H5页面地址
    private final static String LOGO_URL = "http://www.chongdaopet.cn/static/logo.png";//logo图片地址

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
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;
    @Autowired
    private ExpressTransRepository expressTransRepository;
    @Autowired
    private RecommendMapper recommendMapper;

    @Transactional
    @Override
    public ResultResponse initRecommendUrl(String role, Integer id) throws Exception {
        // 推广员类型, 推广员id
        Integer type = getRecommendType(role);
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        RecommendInfo recommendInfo = generateRecommendInfo(type, id);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), recommendInfo);
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

    /**
     * 生成推广信息
     * @param type
     * @param id
     * @return
     */
    private RecommendInfo generateRecommendInfo(Integer type, Integer id) throws Exception {
        String url = RECOMMEND_URL + "?type=" + type + "&recommendId=" + id;
        RecommendInfo ri = new RecommendInfo();
        ri.setRecommendCode(ShareCodeUtil.genShareCode(id, type));
        ri.setRecommenderId(id);
        ri.setType(type);
        ri.setRecommendUrl(url);
        String qrCodeUrl = QrCodeUtils.encode(url, LOGO_URL, "/static/images/", true);
        System.out.println("qrCodeUrl:" + qrCodeUrl);
        ri.setQrCodeUrl("http://47.100.63.167/images/" + qrCodeUrl);
        ri.setCreateTime(new Date());
        return recommendInfoRepository.saveAndFlush(ri);
    }

    @Transactional
    @Override
    public ResultResponse getMyShareInfo(String token) throws Exception {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer type = getRecommendType(tokenVo.getRole());
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        List<RecommendInfo> infos = recommendInfoRepository.findByRecommenderIdAndType(tokenVo.getUserId(), type);
        if(infos == null || infos.size() == 0) {
            //还未生成推广信息
            RecommendInfo recommendInfo = generateRecommendInfo(type, tokenVo.getUserId());
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), generateRecommendInfoVO(recommendInfo));
        } else {
            if (infos.size() == 1) {
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), generateRecommendInfoVO(infos.get(0)));
            } else if (infos.size() > 1) {
                return ResultResponse.createByErrorMessage("查询到该账号下存在多条推广人数据, 请联系管理员确认修改!");
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 生成返回给前台的推广信息实体
     * @param recommendInfo
     * @return
     */
    private RecommendInfoVO generateRecommendInfoVO(RecommendInfo recommendInfo) {
        RecommendInfoVO vo = new RecommendInfoVO();
        BeanUtils.copyProperties(recommendInfo, vo);
        Integer recommenderId = recommendInfo.getRecommenderId();
        User user = userRepository.findById(recommenderId).orElse(null);
        if(user != null) {
            vo.setIcon(user.getIcon());
        }
        return vo;
    }

    @Override
    public boolean isSatisfyOrderRewardQualification(Integer orderId) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
        return isSatisfyOrderReward(orderInfo);
    }

    @Override
    public boolean isSatisfyOrderRewardQualificationByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoRepository.findByOrderNo(orderNo);
        return isSatisfyOrderReward(orderInfo);
    }

    private Boolean isSatisfyOrderReward(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return false;
        }
        Integer userId = orderInfo.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        Integer recommendType = user.getRecommendType();
        if(recommendType == null) {
            return false;
        }
        List<RecommendRecord> list = recommendRecordRepository.findByUserIdAndRecommenderIdAndRecommendType(user.getId(), user.getRecommendId(), user.getRecommendType());
        if(recommendType.equals(RecommendTypeEnum.USER_ROLE.getCode()) || recommendType.equals(RecommendTypeEnum.EXPRESS_ROLE.getCode())) {
            if(list.size() > 0) {
                return false;
            } else {
                return true;
            }
        } else if(recommendType.equals(RecommendTypeEnum.SHOP_ROLE.getCode())) {
            Integer recommendId = user.getRecommendId();//是商店推人时, 这个ID就是shopId
            if(recommendId == orderInfo.getShopId()) {
                //只有非推荐人商铺下的单才计算在返利中
                return false;
            }

            if(list.size() > 4) {
                return false;
            } else {
                //再判定订单中是否含有商品, 不含商品的订单, 当推广人是商家时 也无法进行返利
                //TODO

                return true;
            }
        }
        return false;
    }

    /**
     * 被推广用户首单返利逻辑
     * @param orderId
     * @return
     */
    @Transactional
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

//        if(user.getIsLoginApp() == -1) {
//            return ResultResponse.createByErrorMessage("该用户还未登录过APP, 无法进行返利!");
//            //TODO 将这种用户保存在一个表里, 每天晚上进行定时任务查询, 只要用户后面登录了APP就再将返利给他
//        }

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
        boolean flag = storeReward(recommendRecord.getId(), recommendRecord.getRecommenderId(), recommendRecord.getRecommendType(), recommendRecord.getConsumeType(), recommendRecord.getRewardMoney());
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
     * @param recommenderId
     * @param recommendType
     * @param consumeType
     * @param reward
     * @return
     */
    private boolean storeReward(Integer recommendId, Integer recommenderId, Integer recommendType, Integer consumeType, BigDecimal reward) {
        if (recommendType == RecommendTypeEnum.USER_ROLE.getCode()) {
            //用户
            storeRewardUser(recommenderId, consumeType, getConsumeTypeName(consumeType), reward);
        } else if (recommendType == RecommendTypeEnum.EXPRESS_ROLE.getCode()) {
            //配送员
            storeRewardExpress(recommendId, recommenderId, consumeType, getConsumeTypeName(consumeType), reward);
        } else if (recommendType == RecommendTypeEnum.SHOP_ROLE.getCode()) {
            //商家
            storeRewardShop(recommendId, recommenderId, consumeType, getConsumeTypeName(consumeType), reward);
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

    /**
     * 将返利存入用户账户
     * @param id
     * @param consumeType
     * @param recommendTypeName
     * @param reward
     */
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

    /**
     * 将返利存入配送员账户
     * @param recommendId
     * @param recommenderId
     * @param consumeType
     * @param recommendTypeName
     * @param reward
     */
    private void storeRewardExpress(Integer recommendId, Integer recommenderId, Integer consumeType, String recommendTypeName, BigDecimal reward) {
        Express express = expressRepository.findById(recommenderId).orElse(null);
        if(express != null) {
            BigDecimal money = express.getMoney();
            if(money == null) {
                money = new BigDecimal("0.00");
            }
            express.setMoney(money.add(reward));
            expressRepository.saveAndFlush(express);
            //生成流水记录
            ExpressTrans et = new ExpressTrans();
            et.setExpressId(recommenderId);
            et.setRecommendRecordId(recommendId);
            et.setMoney(reward);
            et.setComment(recommendTypeName);
            et.setType(consumeType);
            et.setCreateTime(new Date());
            expressTransRepository.save(et);
        }
    }

    /**
     * 将返利存入商家账户
     * @param recommendId
     * @param recommenderId
     * @param consumeType
     * @param recommendTypeName
     * @param reward
     */
    private void storeRewardShop(Integer recommendId, Integer recommenderId, Integer consumeType, String recommendTypeName, BigDecimal reward) {
        Shop shop = shopRepository.findById(recommenderId).orElse(null);
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
    @Transactional
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
            boolean flag = storeReward(recommendRecord.getId(), recommendRecord.getRecommenderId(), recommendRecord.getRecommendType(), recommendRecord.getConsumeType(), rewardMoney.multiply(new BigDecimal("-1")));
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

    /**
     * 保险订单完成后触发(如果保险订单填了邀请码的话)
     * @param insuranceOrderId
     * @return
     */
    @Override
    @Transactional
    public boolean recommendInsuranceOrder(Integer insuranceOrderId) {
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        String recommendCode = insuranceOrder.getRecommendCode();
        List<RecommendInfo> riList = recommendInfoRepository.findByRecommendCode(recommendCode);
        if(riList != null && riList.size() > 0) {
            RecommendInfo recommendInfo = riList.get(0);
            Integer type = recommendInfo.getType();
            Integer recommenderId = recommendInfo.getRecommenderId();
            //生成返利记录
            RecommendRecord rr = new RecommendRecord();
            rr.setUserId(insuranceOrder.getUserId());
            rr.setRecommenderId(recommenderId);
            rr.setRecommendType(type);
            rr.setConsumeId(insuranceOrderId);
            rr.setConsumeType(insuranceOrder.getInsuranceType());
            rr.setConsumeMoney(insuranceOrder.getSumAmount());
            rr.setRewardPercent(RecommendTypeEnum.INSURANCE_REWARD_PERCENT.getCode());
            rr.setRewardMoney(rr.getConsumeMoney().multiply(new BigDecimal(rr.getRewardPercent()/100)));
            rr.setIsRefund(-1);
            rr.setCreateTime(new Date());
            recommendRecordRepository.save(rr);
            //根据推荐人类型将资金存入相应账户
            storeReward(rr.getId(), rr.getRecommenderId(), rr.getRecommendType(), rr.getConsumeType(), rr.getRewardMoney());
            return true;
        }
        return false;
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
        return ResultResponse.createBySuccess(resp);
    }

    @Override
    public ResultResponse getRecommendRankList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResultResponse.createBySuccess(new PageInfo(recommendMapper.getRecommendRankList()));
    }

    @Override
    public ResultResponse getMyRecommendDetail(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Map resp = new HashMap();
        List<RecommendRecord> list = recommendRecordRepository.findByRecommenderIdAndIsRefundOrderByCreateTimeDesc(tokenVo.getUserId(), -1);
        List<RecommendRecordVO> voList = new ArrayList<>();
        Set temp = new HashSet();
        for(RecommendRecord rr : list) {
            RecommendRecordVO rrv = new RecommendRecordVO();
            BeanUtils.copyProperties(rr, rrv);
            Integer userId = rr.getUserId();
            User user = userRepository.findById(userId).orElse(null);
            if(user != null) {
                temp.add(userId);
                rrv.setUserName(user.getName());
                rrv.setIcon(user.getIcon());
            }
            voList.add(rrv);
        }
        BigDecimal money = recommendMapper.getMyRecommendTotalMoney(tokenVo.getUserId());
        resp.put("totalMoney", money);
        resp.put("list", voList);
        resp.put("count", temp.size());
        return ResultResponse.createBySuccess(resp);
    }
}
