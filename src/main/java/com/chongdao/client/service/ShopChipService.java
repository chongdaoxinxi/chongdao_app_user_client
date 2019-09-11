package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

public interface ShopChipService {

    /**
     * 导入宠物芯片数据(通过excel)
     * @param token
     * @param file
     * @return
     * @throws IOException
     */
    ResultResponse importShopChipData(String token, MultipartFile file) throws IOException;

    /**
     * 获取宠物芯片数据
     * @return
     */
    ResultResponse getShopShipData(String token, String core, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize);
}
