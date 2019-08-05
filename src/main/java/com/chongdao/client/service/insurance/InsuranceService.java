package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.MedicalInsuranceOrder;
import com.chongdao.client.entitys.PetPickupInsuranceOrder;

public interface InsuranceService {
    ResultResponse saveMedicalIusurance(MedicalInsuranceOrder medicalInsuranceOrder);

    ResultResponse savePetPickupInsurance(PetPickupInsuranceOrder petPickupInsuranceOrder);
}
