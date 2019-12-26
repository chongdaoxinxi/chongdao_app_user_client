package com.chongdao.client.controller.fdfs;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
    @RequestMapping("/api/")
@CrossOrigin
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


    @GetMapping("downloadFile")
    public ResultResponse downloadFile(String url, HttpServletResponse response) throws UnsupportedEncodingException {return uploadService.downloadFile(url, response);}

    /**
     * 下载理赔确认书
     * @param url
     * @param response
     * @return
     */
    @GetMapping("downloadClaimsConfirmation")
    public ResultResponse downloadClaimsConfirmation(String url, HttpServletResponse response) {
        return  null;
    }
}
