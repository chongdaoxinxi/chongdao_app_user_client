package com.chongdao.client.repository;

import com.chongdao.client.entitys.InsuranceTeamAttender;
import com.chongdao.client.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface InsuranceTeamAttenderRepository extends JpaRepository<InsuranceTeamAttender, Integer> {

    /**
     * 获取指定用户的尚未过期的待确认组队
     * @param processAbortTime
     * @param attenderId
     * @return
     */
    @Query(value = "select ita.* from insurance_team_attender ita left join insurance_team it on ita.team_id = it.id where it.create_time < ?1 and ita.attender_id = ?2 and ita.status = 0 and it.status = 1", nativeQuery = true)
    List<InsuranceTeamAttender> getTodoTeamAttender(Date processAbortTime, Integer attenderId);

    /**
     * 获取已经获奖, 拿到保险的用户列表
     * @return
     */
    @Query(value = "select u.* from insurance_team_attender ita left join user u on ita.attender_id = u.id where ita.is_win = 1 order by ita.attend_time desc", nativeQuery = true)
    List<User> getWinAttenderList();

    /**
     * 获取指定队伍已经参与组队的用户
     * @return
     */
    @Query(value = "select u.* from insurance_team_attender ita left join user u on ita.team_id = ?1 and ita.status = 1", nativeQuery = true)
    List<User> getAttendedUserList(Integer teamId);
}
