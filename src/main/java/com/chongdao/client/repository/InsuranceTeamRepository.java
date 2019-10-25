package com.chongdao.client.repository;

import com.chongdao.client.entitys.InsuranceTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceTeamRepository extends JpaRepository<InsuranceTeam, Integer> {
    InsuranceTeam findByBuilderIdAndStatus(Integer builderId, Integer status);
}
