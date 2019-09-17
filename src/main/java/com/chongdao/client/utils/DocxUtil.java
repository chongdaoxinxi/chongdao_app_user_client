package com.chongdao.client.utils;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/28
 * @Version 1.0
 **/
public class DocxUtil {
    public static void main(String[] args) throws IOException, XDocReportException {
//        InputStream inputStream = new ClassPathResource("/template/团省委表格汇总.docx").getInputStream();
//        Map<String, Object> map = new HashMap<>();
//        FileOutputStream fileOutputStream = new FileOutputStream("F:/new.docx");
//        processDocxTemplate(inputStream, fileOutputStream, map, null);
    }

    /**
     * @param in         输入流， 模板
     * @param out        处理后的输出流
     * @param contextMap 上下文，需要绑定的数据
     * @param listTypes  需要循环的对象
     * @throws IOException
     * @throws XDocReportException
     */
    public static void processDocxTemplate(InputStream in, OutputStream out, Map<String, Object> contextMap,
                                           ListType[] listTypes) throws IOException, XDocReportException {
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

        // 2) Create fields metadata to manage lazy loop ([#list Freemarker) for foot
        // notes.
        FieldsMetadata metadata = report.createFieldsMetadata();
        if (listTypes != null) {
            for (ListType type : listTypes) {
                metadata.load(type.getKey(), type.getType(), true);
            }
        }
        // 3) Create context Java model
        IContext context = report.createContext();
        context.putMap(contextMap);
        // 4) Generate report by merging Java model with the Docx
        report.process(context, out);
    }

    public static class ListType {
        private String key;
        private Class<?> type;

        /**
         * 比如 绑定的 context中传入了 students 的 List , 循环中写students.xh 可以循环students取出所有学号
         * 这时key传"students", type 传 Student.class for循环再docx 文件中通过编辑域功能实现， 快捷键 Ctrl+F9
         * ，域名选择MergeField
         *
         * @param key
         * @param type
         */
        public ListType(String key, Class<?> type) {
            this.key = key;
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

    }
}
