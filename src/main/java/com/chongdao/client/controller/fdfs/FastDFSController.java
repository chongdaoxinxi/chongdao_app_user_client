package com.chongdao.client.controller.fdfs;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/")
public class FastDFSController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("upload")
    public ResultResponse<String> uploadFile(@RequestParam("file") MultipartFile file){
       return uploadService.uploadFile(file);
    }

    /**
     * 删除图片
     * @param storagePath
     * @return
     */
    @DeleteMapping("delete")
    public ResultResponse deleteFile(String storagePath){
        return uploadService.deleteFile(storagePath);
    }


}
