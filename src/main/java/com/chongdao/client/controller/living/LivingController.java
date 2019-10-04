package com.chongdao.client.controller.living;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.LivingInfoMapper;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.LivingService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.HTOrderInfoVO;
import com.chongdao.client.vo.LivingInfoVO;
import com.chongdao.client.vo.ProviderSeekFavorVO;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.LivingInfoOrderBy.OrderBy.*;

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

    @Autowired
    private ProviderSeekFavorRepository providerSeekFavorRepository;

    @Autowired
    private LivingInfoMapper livingInfoMapper;


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
            //寻宠详情封装
            this.assembelProviderSeekFavor(userId,user,livingInfoVO);
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


    /**
     * 根据活体类型展示列表
     * 活体类型：0 活体，1领养 ，2.寻宠
     * @param type
     * @return
     */
    @GetMapping("getLivingByType")
    public ResultResponse getLivingByType(@RequestParam Integer type,
                                          @RequestParam String orderBy,
                                          @RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize){
//        PageRequest request = PageRequest.of(pageNum, pageSize);
//        Page<List<LivingInfo>> listPage = livingInfoRepository.findByTypeAndStatus(type,1, request);
        PageHelper.startPage(pageNum,pageSize);
        //排序规则
        if (StringUtils.isNotBlank(orderBy)){
            if (PRICE_ASC_DESC.contains(orderBy) || DISTANCE_ASC_DESC.contains(orderBy) || DISTANCE_ASC_DESC.contains(TIME_ASC_DESC)){
                String[] orderByArray = orderBy.split("_");
                //价格排序、销量排序
                orderBy = orderByArray[0] + " " + orderByArray[1];
            }
        }
        List<LivingInfo> livingInfoList = livingInfoMapper.findByTypeAndStatus(type, orderBy,1);
        if (CollectionUtils.isEmpty(livingInfoList)) {
            livingInfoList.stream().forEach(livingInfo -> {
                User user = userRepository.findById(livingInfo.getUserId()).orElse(null);
                if (user != null) {
                    livingInfo.setUserName(user.getName());
                    livingInfo.setIcon(user.getIcon());
                }
            });
        }
        PageInfo<LivingInfo> pageInfo = new PageInfo<>(livingInfoList);
        pageInfo.setList(livingInfoList);
        return ResultResponse.createBySuccess(pageInfo);
    }




    /**
     * 提供宠物丢失线索
     * @param livingId
     * @param providerUserId
     * @param lostUserId
     * @return
     */
    @PostMapping("providerClues")
    public ResultResponse providerClues(@RequestParam Integer livingId,
                                        @RequestParam Integer providerUserId,
                                        @RequestParam Integer lostUserId){
        ProviderSeekFavor providerSeekFavor = new ProviderSeekFavor();
        providerSeekFavor.setCreateTime(new Date());
        providerSeekFavor.setUpdateTime(new Date());
        providerSeekFavor.setLivingId(livingId);
        providerSeekFavor.setProviderUserId(providerUserId);
        providerSeekFavor.setLostUserId(lostUserId);
        providerSeekFavorRepository.save(providerSeekFavor);
        return ResultResponse.createBySuccess();
    }

    /**
     * 给予奖励
     * @param livingId
     * @param providerUserId
     * @param lostUserId
     * @return
     */
    @PostMapping("reward")
    public ResultResponse reward(@RequestParam Integer livingId,
                                 @RequestParam Integer providerUserId,
                                 @RequestParam Integer lostUserId){
        ProviderSeekFavor providerSeekFavor = providerSeekFavorRepository.findByLivingIdAndLostUserIdAndProviderUserId(livingId, lostUserId, providerUserId);
        if (providerSeekFavor != null) {
            providerSeekFavor.setLostUserId(lostUserId);
            providerSeekFavor.setProviderUserId(providerUserId);
            providerSeekFavor.setLivingId(livingId);
            providerSeekFavor.setStatus(0);
            providerSeekFavorRepository.saveAndFlush(providerSeekFavor);
        }
        return ResultResponse.createBySuccess();
    }



    @GetMapping("getLivingByType")
    public ResultResponse getLivingByType(@RequestParam Integer type,
                                          @RequestParam Integer userId) {
        List<LivingInfo> livingInfoList = Lists.newArrayList();
        if (type.equals("all")) {
            livingInfoList = livingInfoRepository.findByTypeAndStatus(1, userId);
        }else {
            livingInfoList = livingInfoRepository.findByTypeAndStatusAndUserId(type,1, userId);
        }
        return ResultResponse.createBySuccess(livingInfoList);
    }

    /**
     * 封装寻宠详情
     * @param userId
     * @param user
     * @return
     */
    private LivingInfoVO assembelProviderSeekFavor(Integer userId, User user, LivingInfoVO livingInfoVO){
        //给予线索用户列表(需要判断当前查看详情的用户是否是失主本人，如果是则需要标识 "给予奖励" 按钮)
        List<ProviderSeekFavorVO> providerSeekFavorVOList = Lists.newArrayList();
        List<ProviderSeekFavor> providerSeekFavorList = providerSeekFavorRepository.findByLivingId(livingInfoVO.getId());
        if (!CollectionUtils.isEmpty(providerSeekFavorList)) {
            providerSeekFavorList.stream().forEach(providerSeekFavor -> {
                User u = userRepository.findById(providerSeekFavor.getProviderUserId()).orElse(null);
                ProviderSeekFavorVO providerSeekFavorVO = new ProviderSeekFavorVO();
                if (u != null) {
                    if (userId == livingInfoVO.getUserId()) {
                        providerSeekFavorVO.setEnabled(0);
                    }
                    providerSeekFavorVO.setIcon(user.getIcon());
                    providerSeekFavorVO.setStatus(providerSeekFavor.getStatus());
                    providerSeekFavorVO.setUserId(user.getId());
                    providerSeekFavorVO.setUserName(user.getName());
                    providerSeekFavorVO.setCreateTime(providerSeekFavor.getCreateTime());
                }
                providerSeekFavorVOList.add(providerSeekFavorVO);

            });
        }
        livingInfoVO.setProviderSeekFavorVOList(providerSeekFavorVOList);
        return livingInfoVO;
    }




}
