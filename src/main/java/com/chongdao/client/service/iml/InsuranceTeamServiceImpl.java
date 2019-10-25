package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceTeam;
import com.chongdao.client.entitys.InsuranceTeamAttender;
import com.chongdao.client.entitys.User;
import com.chongdao.client.repository.InsuranceTeamAttenderRepository;
import com.chongdao.client.repository.InsuranceTeamRepository;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.InsuranceTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/25
 * @Version 1.0
 **/
public class InsuranceTeamServiceImpl implements InsuranceTeamService {
    @Autowired
    private InsuranceTeamRepository insuranceTeamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InsuranceTeamAttenderRepository insuranceTeamAttenderRepository;


    private final static Integer effectTime = 24;//组队有效期24小时

    @Override
    @Transactional
    public ResultResponse buildInsuranceTeam(Integer builderId) {
        InsuranceTeam insuranceTeam = new InsuranceTeam();
        insuranceTeam.setBuilderId(builderId);
        User user = userRepository.findById(builderId).orElse(null);
        if(user != null) {
            insuranceTeam.setBuilderName(user.getName());
        }
        insuranceTeam.setCreateTime(new Date());
        insuranceTeam.setStatus(1);
        generateRecommendUrl(insuranceTeam);
        return ResultResponse.createBySuccess(insuranceTeamRepository.save(insuranceTeam));
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

    /**
     * 生成分享url以及二维码
     * @param insuranceTeam
     * @return
     */
    private void generateRecommendUrl(InsuranceTeam insuranceTeam) {
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
    public ResultResponse getMyTodoAttend(Integer userId) {
        Date now = new Date();
        Date before = new Date(now.getTime() - effectTime*60*60*1000);
        return ResultResponse.createBySuccess(insuranceTeamAttenderRepository.getTodoTeamAttender(before, userId));
    }

    @Override
    @Transactional
    public ResultResponse confirmAttend(Integer teamAttendId) {
        InsuranceTeamAttender insuranceTeamAttender = insuranceTeamAttenderRepository.findById(teamAttendId).orElse(null);
        if(insuranceTeamAttender != null) {
            insuranceTeamAttender.setStatus(1);
            insuranceTeamAttender.setAttendTime(new Date());
            return ResultResponse.createBySuccess(insuranceTeamAttenderRepository.save(insuranceTeamAttender));
        } else {
            return ResultResponse.createByErrorMessage("无效的ID");
        }
    }

    @Override
    public ResultResponse getAttendDetail(Integer builderId) {
        InsuranceTeam insuranceTeam = insuranceTeamRepository.findByBuilderIdAndStatus(builderId, 1);
        List<User> attendedUserList = new ArrayList<>();
        if(insuranceTeam != null) {
            attendedUserList = insuranceTeamAttenderRepository.getAttendedUserList(insuranceTeam.getId());
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("attendedUserList", attendedUserList);//已经参加组队的用户
        resp.put("winAttenderList", getWinAttenderList());//已经获奖的用户(全平台)
        return ResultResponse.createBySuccess(resp);
    }

    /**
     * 获取已经获奖, 拿到保险的用户列表
     * @return
     */
    private List<User> getWinAttenderList() {
        return insuranceTeamAttenderRepository.getWinAttenderList();
    }
}
