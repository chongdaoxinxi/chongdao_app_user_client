package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.PetCard;

import java.util.List;

/**
 * 宠物卡片接口
 */
public interface PetCardService {
    ResultResponse<PetCard> getPetCardById(Integer id);

    ResultResponse<List<PetCard>> getPetCardByUserIdAndStatus(Integer userId, Integer status);

    ResultResponse savePetCard(PetCard petCard);
}
