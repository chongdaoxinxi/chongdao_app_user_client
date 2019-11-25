package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.GoodsRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.List;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.host}")
    private String hostAddress;

    @Value("${fdfs.url}")
    private String url;

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private GoodsRepository goodsRepository;


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

    @Override
    public ResultResponse convertOldImageToNew() throws IOException {
//        convertShopImage();
        conertGoodImage();
        return null;
    }

    private void convertShopImage() {
        String oldUrl = "";
        List<Shop> all = shopRepository.findAll();
        for(Shop s : all) {
            String logo = s.getLogo();
            if(StringUtils.isNotBlank(logo)) {
                if(logo.indexOf("http") == -1) {
                    oldUrl = logo;
                    oldUrl = "https://www.chongdaopet.com/images/" + oldUrl;
                    String fileName = oldUrl.substring(oldUrl.lastIndexOf("/") + 1);
                    String tempPath = "F:\\temp_image";
                    //根据下载链接, 将图片下载存储到服务器上, 并保存访问url
                    RestTemplate rest = new RestTemplate();
                    rest.execute(oldUrl, HttpMethod.GET, (req) -> {
                    }, (res) -> {
                        InputStream inputStream = res.getBody();
                        FileOutputStream out = new FileOutputStream(tempPath + "\\" + fileName);
                        int byteCount = 0;
                        while ((byteCount = inputStream.read()) != -1) {
                            out.write(byteCount);
                        }
                        out.close();
                        inputStream.close();
                        File file = new File(tempPath + "\\" + fileName);
                        FileInputStream in = new FileInputStream(file);
                        StorePath storePath = this.storageClient.uploadFile(in, file.length(), FilenameUtils.getExtension(fileName), null);
                        String visitUrl = "http://" + url + "/" + storePath.getFullPath();
                        System.out.println("visitUrl:    " + visitUrl);
                        s.setLogo(visitUrl);
                        shopRepository.save(s);
                        in.close();
                        return null;
                    });
                }
            }
        }
        System.out.println("convert shop logo complete!");
    }

    private void conertGoodImage() {
        String oldUrl = "";
        List<Good> all = goodsRepository.findAll();
        for(Good g : all) {
            String icon = g.getIcon();
            if(StringUtils.isNotBlank(icon)) {
                if(icon.indexOf("http") == -1) {
                    oldUrl = icon;
                    oldUrl = "https://www.chongdaopet.com/images/" + oldUrl;
                    String fileName = oldUrl.substring(oldUrl.lastIndexOf("/") + 1);
                    String tempPath = "F:\\temp_image";
                    //根据下载链接, 将图片下载存储到服务器上, 并保存访问url
                    RestTemplate rest = new RestTemplate();
                    rest.execute(oldUrl, HttpMethod.GET, (req) -> {
                    }, (res) -> {
                        InputStream inputStream = res.getBody();
                        FileOutputStream out = new FileOutputStream(tempPath + "\\" + fileName);
                        int byteCount = 0;
                        while ((byteCount = inputStream.read()) != -1) {
                            out.write(byteCount);
                        }
                        out.close();
                        inputStream.close();
                        File file = new File(tempPath + "\\" + fileName);
                        FileInputStream in = new FileInputStream(file);
                        StorePath storePath = this.storageClient.uploadFile(in, file.length(), FilenameUtils.getExtension(fileName), null);
                        String visitUrl = "http://" + url + "/" + storePath.getFullPath();
                        System.out.println("visitUrl:    " + visitUrl);
                        g.setIcon(visitUrl);
                        goodsRepository.save(g);
                        in.close();
                        return null;
                    });
                }
            }
        }
        System.out.println("convert goods icon complete!");
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
//        if(url.indexOf("group") != -1) {
//            url = url.substring(url.indexOf("group"));
//        }
//        String group = url.substring(0, url.indexOf("/"));
//        String path = url.substring(url.indexOf("/") + 1);
//        DownloadByteArray downloadByteArray = new DownloadByteArray();

        try {
            URL url_ = new URL(url);
            HttpURLConnection conn =  null;
            try {
                URLConnection urlCon = url_.openConnection(); // 获取一个URLConnection
                conn = (HttpURLConnection)urlCon;
                conn.setConnectTimeout(5000);//设置连接超时时长
                int code = conn.getResponseCode();//返回连接状态
                if(code == 200){ //表示连接成功
                    System.out.println("连接成功...");
                    InputStream is = null;
                    OutputStream os = null;
                    try{
                        is = conn.getInputStream(); //获取 输入流
//                        os = new FileOutputStream("dog1.jpg");
                        //设置响应头
                        response.reset();
                        response.setContentType("image/jpeg");
                        response.addHeader("Content-Disposition",
                                "attachment;filename=" + URLEncoder.encode("推广链接.jpg", "UTF-8"));
                        os = response.getOutputStream();
                        byte b[] = new byte[1024];
                        int num = 0;
                        while((num = is.read(b)) != -1){
                            os.write(b,0,num);
                        }
                        log.info("下载成功");
                    }catch(IOException e){
                        e.printStackTrace();
                    }finally{
                        is.close();
                        os.close();
                    }
                }else{
                    System.out.println("网络连接异常");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                conn.disconnect();//关闭
                System.out.println("文件下载完成...");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        byte[] bytes = this.storageClient.downloadFile(group, path, downloadByteArray);
//        //设置响应头
//        response.reset();
//        response.setContentType("multipart/form-data");
//        response.addHeader("Content-Disposition",
//                "attachment;filename=" + URLEncoder.encode("店铺二维码", "UTF-8"));
//        try {
//            OutputStream os = response.getOutputStream();
//            os.write(bytes);
//            os.flush();
//            os.close();
//            log.info("下载成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
