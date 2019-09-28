package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodTaste;
import com.chongdao.client.repository.GoodTasteRepository;
import com.chongdao.client.service.GoodTasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/9/28
 * @Version 1.0
 **/
@Service
public class GoodTasteServiceImpl implements GoodTasteService {
    @Autowired
    private GoodTasteRepository goodTasteRepository;

    @Override
    @Transactional
    public ResultResponse saveGoodTaste(GoodTaste goodTaste) {
        goodTaste.setCreateTime(new Date());
        goodTasteRepository.saveAndFlush(goodTaste);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse getGoodTasteList() {
        return ResultResponse.createBySuccess(goodTasteRepository.findAllByOrderByCreateTimeDesc());
    }
}
