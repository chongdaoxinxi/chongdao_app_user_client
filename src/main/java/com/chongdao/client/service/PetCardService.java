package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.PetCard;

import java.util.List;

public interface PetCardService {
    public ResultResponse<PetCard> getPetCardById(Integer id);

    public ResultResponse<List<PetCard>> getPetCardByUserId(Integer userId);

    public ResultResponse<List<PetCard>> getPetCardByUserIdAndStatus(Integer userId, Integer status);
}
