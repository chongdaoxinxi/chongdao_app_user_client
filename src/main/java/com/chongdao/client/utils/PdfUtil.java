package com.chongdao.client.utils;


import com.lowagie.text.FontFactory;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StreamUtils;

import java.io.*;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/28
 * @Version 1.0
 **/
public class PdfUtil {
    public static void main(String[] args) throws IOException, TemplateException, XDocReportException {
//        PracticeApply practiceApply = new PracticeApply();
//        PracticeTeamType practiceTeamType = new PracticeTeamType();
//        practiceTeamType.setName("国情社情观察团");
//        practiceApply.setTeamType(practiceTeamType);
//        Student student = new Student();
//        student.setName("李铁");
//        student.setXh("10255");
//        student.setTelephone("13012345678");
//        practiceApply.setProposer(student);
//        practiceApply.setName("计算机学院考察团");
//        practiceApply.setDuty("tuanzhishu");
//        practiceApply.setWbName("");
//        practiceApply.setWxName("");
//        practiceApply.setIntro("");
//        InputStream inputStream = new ClassPathResource("/template/applySingle.docx").getInputStream();
//        Map<String, Object> map = new HashMap<>();
//        map.put("apply", practiceApply);
//        FileOutputStream fileOutputStream = new FileOutputStream("F:/new.docx");
//        DocxUtil.processDocxTemplate(inputStream, fileOutputStream, map, null);
//        fileOutputStream.flush();
//        fileOutputStream.close();
//        inputStream.close();
//        FileOutputStream outputStream = new FileOutputStream("F:/new.pdf");
//        convertPdf(new FileInputStream("F:/new.docx"), outputStream);
//        outputStream.close();
    }

    static {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = patternResolver.getResources("classpath*:/font/**");
            File file = new File("fonts");
            if (!file.exists()) {
                file.mkdirs();
            }
            for (Resource r : resources) {
                if (StringUtils.isEmpty(r.getFilename())) {
                    continue;
                }
                try (InputStream in = r.getInputStream();
                     FileOutputStream fout = new FileOutputStream(new File(file, r.getFilename()))) {
                    StreamUtils.copy(in, fout);
                    in.close();
                    fout.close();
                }
            }
            FontFactory.registerDirectory(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param inputStream 必须为docx流
     * @param output
     * @throws IOException
     */
    public static void convertPdf(InputStream inputStream, OutputStream output) throws IOException {
        XWPFDocument document = new XWPFDocument(inputStream);
        // 2) Convert POI XWPFDocument 2 PDF with iText
        PdfOptions options = PdfOptions.create();
        options.fontProvider(ITextFontRegistry.getRegistry());
        PdfConverter.getInstance().convert(document, output, options);
    }
}
