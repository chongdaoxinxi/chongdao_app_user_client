package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceTeam;
import com.chongdao.client.entitys.InsuranceTeamAttender;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.UserStatusEnum;
import com.chongdao.client.repository.InsuranceTeamAttenderRepository;
import com.chongdao.client.repository.InsuranceTeamRepository;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.InsuranceTeamService;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.GeneratorUserName;
import com.chongdao.client.utils.QrCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/25
 * @Version 1.0
 **/
@Service
public class InsuranceTeamServiceImpl implements InsuranceTeamService {
    @Autowired
    private InsuranceTeamRepository insuranceTeamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InsuranceTeamAttenderRepository insuranceTeamAttenderRepository;
    @Autowired
    private SmsService smsService;


    private final static String RECOMMEND_URL = "http://www.chongdaopet.cn/pintuan/baoxian/bx_index.html";//推广页面url
    private final static Integer EFFECT_TIME = 24;//组队有效期24小时

    @Override
    @Transactional
    public ResultResponse signAndBuildInsuranceTeam(String phone, String code) throws Exception {
        //检验验证码是否正确
        if (StringUtils.isNoneBlank(smsService.getSmsCode(phone))) {
            if (smsService.getSmsCode(phone).equals(code)) {
                User u = signUser(phone);
                return buildInsuranceTeam(u.getId());
            }
        }
        return ResultResponse.createByError();
    }

    @Override
    @Transactional
    public ResultResponse buildInsuranceTeam(Integer builderId) throws Exception {
        InsuranceTeam old = insuranceTeamRepository.findByBuilderIdAndStatus(builderId, 1);
        if(old != null) {
            return ResultResponse.createByErrorMessage("存在生效中的组队, 不能再次发起!");
        }
        InsuranceTeam insuranceTeam = new InsuranceTeam();
        insuranceTeam.setBuilderId(builderId);
        User user = userRepository.findById(builderId).orElse(null);
        if(user != null) {
            insuranceTeam.setBuilderName(user.getName());
        }
        insuranceTeam.setCreateTime(new Date());
        insuranceTeam.setStatus(1);
        InsuranceTeam save = insuranceTeamRepository.save(insuranceTeam);
        generateRecommendUrl(save);
        return ResultResponse.createBySuccess(insuranceTeamRepository.save(save));
    }

    @Override
    @Transactional
    public ResultResponse visitRecommendUrl(Integer builderId) {
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findByBuilderIdAndStatus(builderId, 1);
        Integer visitTimes = insuranceTeam.getVisitTimes();
        if(visitTimes == null) {
            visitTimes = 1;
        } else {
            visitTimes = visitTimes + 1;
        }
        insuranceTeam.setVisitTimes(visitTimes);
        insuranceTeamRepository.save(insuranceTeam);
        return ResultResponse.createBySuccess();
    }

    @Override
    @Transactional
    public ResultResponse signAndAttendInsuranceTeam(String phone, String code, Integer teamId) {
        //检验验证码是否正确
        if (StringUtils.isNoneBlank(smsService.getSmsCode(phone))) {
            if (smsService.getSmsCode(phone).equals(code)) {
                User u = signUser(phone);
                return attendInsuranceTeam(teamId, u.getId());
            }
        }
        return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USER_CODE_ERROR.getStatus(), UserStatusEnum.USER_CODE_ERROR.getMessage());
    }

    private User signUser(String phone) {
        User u = userRepository.findByPhone(phone);
        if(u == null) {
            u.setPhone(phone);
            u.setName(phone);
            u.setLastLoginTime(new Date());
            u.setType(1);
            u.setStatus(1);
            u.setCreateTime(new Date());
           return userRepository.save(u);
        }
        return u;
    }

    /**
     * 生成分享url以及二维码
     * @param insuranceTeam
     * @return
     */
    private void generateRecommendUrl(InsuranceTeam insuranceTeam) throws Exception {
        //生成url
        String url = RECOMMEND_URL + "?teamId=" + insuranceTeam.getId();
        insuranceTeam.setUrl(url);
        //根据url生成二维码图片, 并保存在服务器, 然后保存访问路径
        String qrCodeUrl = QrCodeUtils.encode(url, "https://www.chongdaopet.cn/images/logo.png", "/static/images/", true);
        System.out.println("qrCodeUrl:" + qrCodeUrl);
        insuranceTeam.setQrCodeUrl("http://47.100.63.167/images/" + qrCodeUrl);

    }

    @Override
    @Transactional
    public ResultResponse attendInsuranceTeam(Integer teamId, Integer attenderId) {
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findById(teamId).orElse(null);
        Integer builderId = insuranceTeam.getBuilderId();
        InsuranceTeamAttender ita = new InsuranceTeamAttender();
        ita.setTeamId(teamId);
        ita.setBuilderId(builderId);
        ita.setUserId(attenderId);
        User user = userRepository.findById(attenderId).orElse(null);
        if(user != null) {
            ita.setName(user.getName());
        }
        ita.setCreateTime(new Date());
        ita.setType(2);//加入者
        ita.setIsWin(0);//待开奖
        ita.setStatus(0);//待确认
        return ResultResponse.createBySuccess(insuranceTeamAttenderRepository.save(ita));
    }

    @Override
    public ResultResponse attendInsuranceTeamInApp(Integer teamId, Integer attenderId) {
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findById(teamId).orElse(null);
        Integer builderId = insuranceTeam.getBuilderId();
        InsuranceTeamAttender ita = new InsuranceTeamAttender();
        ita.setTeamId(teamId);
        ita.setBuilderId(builderId);
        ita.setUserId(attenderId);
        User user = userRepository.findById(attenderId).orElse(null);
        if(user != null) {
            ita.setName(user.getName());
        }
        ita.setCreateTime(new Date());
        ita.setType(2);//加入者
        ita.setIsWin(0);//待开奖
        ita.setStatus(1);//待确认
        return ResultResponse.createBySuccess(insuranceTeamAttenderRepository.save(ita));
    }

    @Override
    public ResultResponse getMyTodoAttend(Integer userId) {
        Date now = new Date();
        Date before = new Date(now.getTime() - EFFECT_TIME*60*60*1000);
        return ResultResponse.createBySuccess(insuranceTeamAttenderRepository.getTodoTeamAttender(before, userId));
    }

    @Override
    public ResultResponse getMyTodoBuild(Integer builderId) {
        List<InsuranceTeam> list = new ArrayList<>();
        list.add(insuranceTeamRepository.findByBuilderIdAndStatus(builderId, 0));
        return ResultResponse.createBySuccess(list);
    }

    @Override
    @Transactional
    public ResultResponse confirmAttend(Integer attenderId, Integer teamId) {
        InsuranceTeamAttender insuranceTeamAttender = insuranceTeamAttenderRepository.findByTeamIdAndUserId(teamId, attenderId);
        if(insuranceTeamAttender != null) {
            insuranceTeamAttender.setStatus(1);
            insuranceTeamAttender.setAttendTime(new Date());
            insuranceTeamAttenderRepository.save(insuranceTeamAttender);
            //确认是否需要添加机器人
            List<InsuranceTeamAttender> list = insuranceTeamAttenderRepository.findByTeamId(teamId);
            if(list.size() == 6) {
                //添加完第6个队员的时候, 检测是否需要添加机器人
                Boolean needToAddRobot = isNeedToAddRobot(list, teamId);
                if(needToAddRobot) {
                    systemAutoAddAttender(teamId);
                }
            }
            return ResultResponse.createBySuccess();
        } else {
            return ResultResponse.createByErrorMessage("无效的ID");
        }
    }

    @Override
    @Transactional
    public ResultResponse confirmBuild(Integer teamId) {
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findById(teamId).orElse(null);
        insuranceTeam.setStatus(1);
        insuranceTeamRepository.save(insuranceTeam);
        return ResultResponse.createBySuccess();
    }

    private Boolean isNeedToAddRobot(List<InsuranceTeamAttender> list, Integer teamId) {
        boolean needToAdd = true;
        for(InsuranceTeamAttender attender : list) {
            Integer id = attender.getId();
            Integer left = id%200;//如果该队伍包含200的倍数id的队员, 那么不往里面添加机器人
            if(left == 0) {
                needToAdd = false;
            } else {
                Integer isSystem = attender.getIsSystem();
                if(isSystem == 1) {
                    needToAdd = false;
                }
            }
        }
        return  needToAdd;
    }

    @Override
    public ResultResponse systemAutoAddAttender(Integer teamId) {
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findById(teamId).orElse(null);
        if(insuranceTeam != null) {
            addRobot(insuranceTeam.getBuilderId(), teamId);//添加一个机器人
            addRobot(insuranceTeam.getBuilderId(), teamId);//添加一个机器人
            return ResultResponse.createBySuccess();
        } else {
            return ResultResponse.createByErrorMessage("无效的teamId");
        }
    }

    @Override
    public ResultResponse getAttendDetail(Integer teamId) {
        List<Integer> userIdList = insuranceTeamAttenderRepository.getAttendedUserList(teamId);
        List<User> attendedUserList = new ArrayList<>();
        for(Integer id : userIdList) {
            attendedUserList.add(userRepository.findById(id).orElse(null));
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("attendedUserList", attendedUserList);//已经参加组队的用户
        resp.put("winAttenderList", getWinAttenderList());//已经获奖的用户(全平台)
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findById(teamId).orElse(null);
        resp.put("createTime", insuranceTeam.getCreateTime());//队伍创建时间
        return ResultResponse.createBySuccess(resp);
    }

    @Override
    public ResultResponse getMyTeamUrl(Integer builderId) {
        InsuranceTeam team = insuranceTeamRepository.findByBuilderIdAndStatus(builderId, 1);
        return ResultResponse.createBySuccess(team);
    }

    /**
     * 添加一个机器人队员
     */
    private void addRobot(Integer builderId, Integer teamId) {
        //生成的robot的userId是null
        InsuranceTeamAttender attender = new InsuranceTeamAttender();
        attender.setName(GeneratorUserName.getRandomJianHan(4));//TODO, 需要一个随机逻辑来生成机器人名称
        attender.setBuilderId(builderId);
        attender.setTeamId(teamId);
        attender.setStatus(1);
        attender.setIsSystem(1);
        attender.setIsWin(0);
        attender.setType(2);
        attender.setCreateTime(new Date());
        attender.setAttendTime(new Date());
        insuranceTeamAttenderRepository.save(attender);
    }

    @Override
    public ResultResponse systemLuckyDraw(Integer teamId) {
        List<InsuranceTeamAttender> list = insuranceTeamAttenderRepository.findByTeamId(teamId);
        InsuranceTeamAttender robotAttender = getRobotAttender(list);
        if(robotAttender == null) {
            //正常开奖
            Random rn = new Random();
            int luckyIndex = rn.nextInt(12);
            luckyDraw(list, list.get(luckyIndex));
        } else {
            //指定机器人开奖
            luckyDraw(list, robotAttender);
        }
        return ResultResponse.createBySuccess();
    }

    private void luckyDraw(List<InsuranceTeamAttender> list, InsuranceTeamAttender attender) {
        for (InsuranceTeamAttender teamAttender : list) {
            if(teamAttender.getId() == attender.getId()) {
                teamAttender.setIsWin(1);
            } else {
                teamAttender.setIsWin(-1);
            }
            insuranceTeamAttenderRepository.save(teamAttender);
        }
    }

    private InsuranceTeamAttender getRobotAttender(List<InsuranceTeamAttender> list) {
        List<InsuranceTeamAttender> robotList = new ArrayList<>();
        for(InsuranceTeamAttender attender : list) {
            Integer isSystem = attender.getIsSystem();
            if(isSystem != null && isSystem == 1) {
                robotList.add(attender);
            }
        }
        return robotList.get(0);
    }

    /**
     * 获取已经获奖, 拿到保险的用户列表
     * @return
     */
    private List<User> getWinAttenderList() {
        List<Integer> userIdList = insuranceTeamAttenderRepository.getWinAttenderList();
        List<User> userList = new ArrayList<>();
        for(Integer id : userIdList) {
            userList.add(userRepository.findById(id).orElse(null));
        }
        return userList;
    }
}
