package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.host}")
    private String hostAddress;


    @Override
    public ResultResponse<String> uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty() || file == null){
                return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
            }
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String fileUrl = hostAddress + storePath.getFullPath();
            return ResultResponse.createBySuccess(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultResponse.createByErrorMessage(e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param storagePath
     * @return
     */
    @Override
    public ResultResponse deleteFile(String storagePath) {
        if (StringUtils.isBlank(storagePath)){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        storageClient.deleteFile(storagePath);
        return ResultResponse.createBySuccess();
    }
}
