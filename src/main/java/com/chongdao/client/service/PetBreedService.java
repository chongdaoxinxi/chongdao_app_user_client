package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface PetBreedService {
    ResultResponse getPetBreedByType(Integer type, String name);
}
