package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.PetCard;
import com.chongdao.client.service.PetBreedService;
import com.chongdao.client.service.PetCardService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 宠物资料卡片
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/petCard/")
public class UserPetCardController {
    @Autowired
    private PetCardService petCardService;
    @Autowired
    private PetBreedService petBreedService;

    /**
     * 获取宠物品种
     * @param type
     * @return
     */
    @GetMapping("getPetBreed")
    public ResultResponse getPetBreed(Integer type, String name) {
        return petBreedService.getPetBreedByType(type, name);
    }

    /**
     *  获取用户的宠物卡片列表
     * @param token
     * @param status
     * @return
     */
    @GetMapping("getPetCardList")
    public ResultResponse<List<PetCard>> getPetCardList(String token, Integer status) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return petCardService.getPetCardByUserIdAndStatus(tokenVo.getUserId(), status);
    }

    /**
     * 获取宠物卡片详细信息
     * @param cardId
     * @return
     */
    @GetMapping("getPetCardInfo")
    public ResultResponse<PetCard> getPetCardInfo(Integer cardId) {
        return petCardService.getPetCardById(cardId);
    }

    /**
     * 保存宠物卡片信息
     * @param petCard
     * @return
     */
    @PostMapping("savePetCard")
    public ResultResponse savePetCard(PetCard petCard) {
        return petCardService.savePetCard(petCard);
    }
}
