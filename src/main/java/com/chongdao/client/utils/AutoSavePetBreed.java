package com.chongdao.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Description 用于将抓取到的猫/狗品种txt文件的内容-->>转换为sql需要的语句
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
//@Component
public class AutoSavePetBreed implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        InputStream i = new FileInputStream("F:\\cat.txt");
        String line;
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(i));
        line = reader.readLine();
        while(line != null) {
            buffer.append("(");
            String code = PinYin2Abbreviation.cn2py(line);
            buffer.append("'");
            if(StringUtils.isBlank(code)) {
                buffer.append("");
            } else {
                buffer.append(code);
            }
            buffer.append("'");
            buffer.append(",");
            buffer.append("'");
            buffer.append(line);
            buffer.append("'");
            buffer.append(",");
            buffer.append(2);

            buffer.append(")");
            buffer.append(",");
            line = reader.readLine();
        }
        reader.close();
        i.close();
        System.out.println(buffer.toString());
    }
}
