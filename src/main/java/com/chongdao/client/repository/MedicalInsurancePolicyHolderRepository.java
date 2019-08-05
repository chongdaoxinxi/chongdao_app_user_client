package com.chongdao.client.repository;

import com.chongdao.client.entitys.MedicalInsuranceRecognizee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInsurancePolicyHolderRepository extends JpaRepository<MedicalInsuranceRecognizee, Integer> {
}
