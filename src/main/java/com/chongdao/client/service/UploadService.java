package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface UploadService {

    ResultResponse<String> uploadFile(MultipartFile file);

    /**
     * 将旧服务器上的图片转到新服务器
     * @return
     */
    ResultResponse convertOldImageToNew() throws IOException;

    ResultResponse deleteFile(String storagePath);

    ResultResponse downloadFile(String url, HttpServletResponse response) throws UnsupportedEncodingException;
}
