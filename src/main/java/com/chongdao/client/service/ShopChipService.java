package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ShopChipService {

    /**
     * 导入宠物芯片数据(通过excel)
     * @param token
     * @param file
     * @return
     * @throws IOException
     */
    ResultResponse importShopChipData(String token, MultipartFile file) throws IOException;
}
