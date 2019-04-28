package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.PetCard;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.PetCardRepository;
import com.chongdao.client.service.PetCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResultResponse<PetCard>  getPetCardById(Integer id) {
//        return petCardRepository.findById(id).get();
        if(id == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }
        return null;
    }

    @Override
    public ResultResponse<List<PetCard>> getPetCardByUserId(Integer userId) {
        return null;
//        return petCardRepository.findByUserId(userId).get();
    }

    @Override
    public ResultResponse<List<PetCard>> getPetCardByUserIdAndStatus(Integer userId, Integer status) {
        return null;
//        return petCardRepository.findByUserIdAndStatus(userId, status).get();
    }
}
