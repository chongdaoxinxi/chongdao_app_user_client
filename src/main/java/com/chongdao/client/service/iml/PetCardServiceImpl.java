package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.PetCard;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.PetCardRepository;
import com.chongdao.client.service.PetCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/4/25
 * @Version 1.0
 **/
@Service
public class PetCardServiceImpl implements PetCardService {
    @Autowired
    private PetCardRepository petCardRepository;

    @Override
    public ResultResponse<PetCard>  getPetCardById(Integer cardId) {
        return Optional.ofNullable(cardId).map(id ->
                ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), petCardRepository.findById(id).orElse(null)))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse<List<PetCard>> getPetCardByUserIdAndStatus(Integer userId, Integer status) {
        if(Optional.ofNullable(userId).isPresent() && Optional.ofNullable(status).isPresent()) {
            return  ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), petCardRepository.findByUserIdAndStatus(userId, status).orElse(null));
        } else if(Optional.ofNullable(userId).isPresent()) {
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), petCardRepository.findByUserId(userId).orElse(null));
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse savePetCard(PetCard petCard) {
        if(Optional.of(petCard).isPresent()) {
            if(petCard.getId() == null) {
                petCard.setCreateTime(new Date());
            }
            petCardRepository.saveAndFlush(petCard);
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage());
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse removePetCard(Integer petCardId) {
        if(petCardId != null) {
            PetCard petCard = petCardRepository.findById(petCardId).orElse(null);
            if(petCard != null) {
                petCard.setStatus(-1);
                return ResultResponse.createBySuccess("删除成功!", petCardRepository.saveAndFlush(petCard));
            } else {
                return ResultResponse.createByErrorMessage("无效的卡片ID");
            }
        } else {
            return ResultResponse.createByErrorMessage("卡片ID不能为空!");
        }
    }
}
