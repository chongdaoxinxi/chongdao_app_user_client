package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.host}")
    private String hostAddress;


    @Override
    public synchronized ResultResponse<String> uploadFile(MultipartFile file) {
        if (file.isEmpty() || file == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        try {
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String fileUrl = hostAddress + "/" + storePath.getFullPath();
            //加上http前缀
            fileUrl = "http://" + fileUrl;
            return ResultResponse.createBySuccess(fileUrl);
        } catch (IOException e) {
            log.error("【上传图片】上传失败，error={}", e.getMessage());
            return ResultResponse.createByErrorMessage(e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param storagePath
     * @return
     */
    @Override
    public synchronized ResultResponse deleteFile(String storagePath) {
        if (StringUtils.isBlank(storagePath)) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        try {
            storageClient.deleteFile(storagePath);
        } catch (Exception e) {
            log.error("【删除图片】删除失败, error={}", e.getMessage());
            return ResultResponse.createByErrorMessage(e.getMessage());
        }
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse downloadFile(String url, HttpServletResponse response) throws UnsupportedEncodingException {
        if(url.indexOf("group") != -1) {
            url = url.substring(url.indexOf("group"));
        }
        String group = url.substring(0, url.indexOf("/"));
        String path = url.substring(url.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = this.storageClient.downloadFile(group, path, downloadByteArray);
        //设置响应头
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + path);// 设置文件名
        response.setHeader("Context-Type", "application/xmsdownload");

        response.reset();
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(path, "UTF-8"));


        try {
            OutputStream os = response.getOutputStream();
            for(int i=0; i<bytes.length;i++) {
                os.write(bytes[i]);
            }
            os.flush();
            os.close();
            log.info("下载成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
