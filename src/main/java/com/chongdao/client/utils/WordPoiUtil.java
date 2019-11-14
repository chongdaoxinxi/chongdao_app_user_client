package com.chongdao.client.utils;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Description word poi 工具类
 * @Author onlineS
 * @Date 2019/11/12
 * @Version 1.0
 **/
public class WordPoiUtil {

    public static String[] readOnWord(String filePath) {
        File file = new File(filePath);
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String doc1 = extractor.getText();
            String[] split = doc1.split("、");
            System.out.println(doc1);
            fis.close();
            return split;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
