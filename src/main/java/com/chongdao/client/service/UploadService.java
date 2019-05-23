package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResultResponse<String> uploadFile(MultipartFile file);

    ResultResponse deleteFile(String storagePath);
}
