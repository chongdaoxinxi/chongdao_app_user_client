package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface UploadService {

    ResultResponse<String> uploadFile(MultipartFile file);

    ResultResponse deleteFile(String storagePath);

    ResultResponse downloadFile(String url, HttpServletResponse response) throws UnsupportedEncodingException;
}
