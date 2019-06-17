package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.CategoryRepository;
import com.chongdao.client.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/14
 * @Version 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResultResponse getCategoryData() {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), categoryRepository.findByStatus(1));
    }
}
