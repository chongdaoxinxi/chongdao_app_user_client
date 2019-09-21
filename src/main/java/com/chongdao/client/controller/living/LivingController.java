package com.chongdao.client.controller.living;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.LivingCollect;
import com.chongdao.client.entitys.LivingInfo;
import com.chongdao.client.entitys.Support;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.LivingService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.HTOrderInfoVO;
import com.chongdao.client.vo.LivingInfoVO;
import com.chongdao.client.vo.ResultTokenVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author fenglong
 * @date 2019-09-12 12:41
 */
@RestController
@RequestMapping("/api/l/")
@Slf4j
public class LivingController {

    @Autowired
    private LivingInfoRepository livingInfoRepository;

    @Autowired
    private PetBreedRepository breedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LivingCollectRepository livingCollectRepository;

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private LivingService livingService;


    /**
     * 发布活体
     * @param livingInfo
     * @param bindingResult
     * @return
     */
    @PostMapping("add")
    public ResultResponse<LivingInfo> add(@Valid LivingInfo livingInfo, BindingResult bindingResult){
        LoginUserUtil.resultTokenVo(livingInfo.getToken());
        if (bindingResult.hasErrors()) {
            log.error("【发布活体】参数不正确，livingInfo={}:",livingInfo);
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),bindingResult.getFieldError().getDefaultMessage());
        }
        livingInfoRepository.save(livingInfo);
        return ResultResponse.createBySuccess();
    }


    /**
     * 宠物品种列表
     * @return
     */
    @GetMapping("list")
    public ResultResponse list(){
        return ResultResponse.createBySuccess(breedRepository.findAll());
    }

    /**
     * 根据宠物品种id查询信息(已上架)
     * @param petBreedId
     * @return
     */
    @GetMapping("findByPetTypeId")
    public ResultResponse findByPetTypeId(@RequestParam Integer petBreedId, String param,int pageNum,int pageSize){
        PageRequest request = PageRequest.of(pageNum, pageSize);
        Page<List<LivingInfo>> listPage = Page.empty();
        if (petBreedId != null && StringUtils.isNotBlank(param)){
            if (StringUtils.equals(param,"asc")){
                //价格升序
                listPage = livingInfoRepository.findByPetTypeIdAndStatusOrderByPriceAsc(petBreedId, request, 1);
            } else {
                //价格降序
                listPage = livingInfoRepository.findByPetTypeIdAndStatusOrderByPriceDesc(petBreedId, request, 1);
            }
        }else {
            listPage = livingInfoRepository.findByPetTypeIdAndStatus(petBreedId, request, 1);
        }
        return ResultResponse.createBySuccess(listPage.getContent());
    }


    /**
     * 活体详情
     * @param id
     * @return
     */
    @GetMapping("detail")
    public ResultResponse detail(@RequestParam Integer id, Integer userId){
        LivingInfo livingInfo = livingInfoRepository.findById(id).orElse(null);
        LivingInfoVO livingInfoVO = null;
        if (livingInfo != null){
            User user = userRepository.findById(livingInfo.getUserId()).orElse(null);
            if (user != null) {
                livingInfoVO = new LivingInfoVO();
                BeanUtils.copyProperties(livingInfo, livingInfoVO);
                livingInfoVO.setUserName(user.getName());
            }

            //判断当前用户是否点赞
            if (userId != null) {
                Support support = supportRepository.findByUserIdAndLivingId(userId, id);
                if (support != null){
                    livingInfoVO.setEnabledSupport(1);
                }
            }
        }
        return ResultResponse.createBySuccess(livingInfoVO);
    }

    /**
     * 收藏活体
     * @param token
     * @param livingId
     * @param status 是否收藏 0 否 1 是
     * @return
     */
    @PostMapping("collect")
    public ResultResponse collect( @RequestParam Integer livingId, @RequestParam Integer status,@RequestParam String token){
        ResultTokenVo tokenVo =  LoginUserUtil.resultTokenVo(token);
        //收藏先查询是否存在该活体
        LivingCollect livingCollect = livingCollectRepository.findByUserIdAndLivingId(tokenVo.getUserId(), livingId);
        if (livingCollect != null) {
            livingCollect.setId(livingCollect.getId());
        }else{
            livingCollect.setCreateTime(new Date());
        }
        livingCollect.setEnabledCollect(status);
        livingCollect.setUserId(tokenVo.getUserId());
        livingCollect.setLivingId(livingId);
        livingCollect.setUpdateTime(new Date());
        livingCollectRepository.save(livingCollect);
        return ResultResponse.createBySuccess();
    }


    /**
     * 点赞
     * @return
     */
    @PostMapping("support")
    public ResultResponse support(@RequestParam Integer livingId,
                                  @RequestParam Integer status,@RequestParam String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Support support = supportRepository.findByUserIdAndLivingId(tokenVo.getUserId(), livingId);
        if (support != null) {
            support.setId(support.getId());
        }
        support.setStatus(status);
        support.setUserId(tokenVo.getUserId());
        support.setLivingId(livingId);
        supportRepository.save(support);
        return ResultResponse.createBySuccess();
    }


    /**
     * 预下单/下单
     * 订单类型 1：活体 2领养
     * 服务类型：1 单程 2到店自取
     * @param htOrderInfoVO
     * @param bindingResult
     * @return
     */
    @PostMapping("preOrCreateOrder")
    public ResultResponse preOrCreateOrder(@RequestBody @Valid HTOrderInfoVO htOrderInfoVO, BindingResult bindingResult){
        LoginUserUtil.resultTokenVo(htOrderInfoVO.getToken());
        if (bindingResult.hasErrors()) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),bindingResult.getFieldError().getDefaultMessage());
        }
        return livingService.preOrCreateOrder(htOrderInfoVO);
    }













}
