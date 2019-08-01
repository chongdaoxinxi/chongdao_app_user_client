package com.chongdao.client;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChongdaoAppUserClientApplicationTests {

    @Test
    public void contextLoads() {

    }

    File file = null;
    Set<MateData> metaDataSet = null;

    @Before
    public void newFile() {
        file = new File("/Users/fenglong/Downloads/1.jpg");
    }

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 测试1--图片上传
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        //上传图片
        StorePath storePath = this.storageClient.uploadFile(new FileInputStream(file), file.length(), "jpg", metaDataSet);
        printlnPath(storePath);
    }

    private void printlnPath(StorePath storePath) {
        //组名
        System.out.println("【组名】:" + storePath.getGroup());
        //带组名的文件地址
        System.out.println("【带组名的文件地址】:" + storePath.getFullPath());
        //不带组名的文件地址
        System.out.println("【不带组名的文件地址】:" + storePath.getPath());
    }


    /**
     * 查询
     */
    @Test
    public void testQuery() {
        FileInfo fileInfo = this.storageClient.queryFileInfo("group1", "M00/00/00/rBEAClzk74KAG6DSAACgHQecVPw161.jpg");
        System.out.println("图片信息如下：\n" + fileInfo.getCrc32() + "\n" + new Date(fileInfo.getCreateTime()) + "\n" + fileInfo.getFileSize() + "\n" + fileInfo.getSourceIpAddr());
    }

    @Test
    public void testContains(){
        String str = "25-30kg";
        System.out.println(str.contains("25kg"));
    }


    @Test
    public  void test() {
        String str="abcdabcdabcdabcd";
        int count=0;
//找出所有"abcd"的位置，并且打印出现次数，（循环+指定起始位置）
        int t=0;
        for(int i=0;i<str.length();i++){
            t=str.indexOf("abcd",i);
            if(t!=-1){
                System.out.println("出现的索引为："+t);//打印出出现得索引
// System.out.println(str.substring(t,t+"abcd".length()));//此句话是验证是否匹配的，它的结果是"abcd"
                count++;//次数增加
                i=t;//指定起始位置
            }
        }
        System.out.println("出现的次数为："+count);
    }



}
