package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.repository.InsuranceShopChipRepository;
import com.chongdao.client.repository.PetCardRepository;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.webservice.EcooperationWebService;
import com.chongdao.client.service.insurance.webservice.EcooperationWebServiceService;
import com.chongdao.client.utils.DocxUtil;
import com.chongdao.client.utils.Md5;
import com.chongdao.client.utils.PdfUtil;
import fr.opensagres.xdocreport.core.XDocReportException;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/31
 * @Version 1.0
 **/
@Service
public class InsuranceExternalServiceImpl implements InsuranceExternalService {
    @Autowired
    private PetCardRepository petCardRepository;
    @Autowired
    private InsuranceShopChipRepository insuranceShopChipRepository;
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;

    private static final String COMPANY_CODE = "CDXX";
    private static final String INSURANCE_URL = "http://partnertest.mypicc.com.cn/ecooperation/webservice/insure?wsdl";
    private static final String SECRET_KEY = "Picc37mu63ht38mw";
    private static final String INSURANCE_SERVICE_NO = "001001";
    private static final String ZFO_RISK_CODE = "ZFO";
    private static final String ZFO_RATION_TYPE = "ZFO310000a";
    private static final String ZCG_RISK_CODE = "ZCG";
    private static final String ZCG_RATION_TYPE = "ZCG3199001";
    private static final String I9Q_RISK_CODE = "I9Q";
    private static final String I9Q_RATION_TYPE = "I9Q310000a";
    private static final String POLICY_FOLDER_PREFIX = "../policy/";
    private static final String POLICY_REALPATH = "/home/policy/";

    private String ZFOForm = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>" +
            "<ApplyInfo>\n" +
            "\t<GeneralInfo>\n" +
            "\t\t<UUID>${UUID}</UUID>\n" +
            "\t\t<PlateformCode>CPI000865</PlateformCode>\n" +
            "\t\t<Md5Value>${Md5Value}</Md5Value>\n" +
            "\t</GeneralInfo>\n" +
            "\t<PolicyInfos>\n" +
            "\t\t<PolicyInfo>\n" +
            "\t\t\t<SerialNo>${SerialNo}</SerialNo>\n" +
            "\t\t\t<RiskCode>${RiskCode}</RiskCode>\n" +
            "\t\t\t<OperateTimes>${OperateTimes}</OperateTimes>\n" +
            "\t\t\t<StartDate>${StartDate}</StartDate>\n" +
            "\t\t\t<EndDate>${EndDate}</EndDate>\n" +
            "\t\t\t<StartHour>0</StartHour>\n" +
            "\t\t\t<EndHour>24</EndHour>\n" +
            "\t\t\t<SumAmount>${SumAmount}</SumAmount>\n" +
            "\t\t\t<SumPremium>${SumPremium}</SumPremium>\n" +
            "\t\t\t<ArguSolution>1</ArguSolution>\n" +
            "\t\t\t<Quantity>1</Quantity>\n" +
            "\t\t\t<PayWay>Y</PayWay>\n" +
            "\t\t\t<InsuredPlan>\n" +
            "\t\t\t\t<RationType>${RationType}</RationType>\n" +
            "\t\t\t</InsuredPlan>\n" +
            "\t\t\t<Applicant>\n" +
            "\t\t\t\t<AppliName>${AppliName}</AppliName>\n" +
            "\t\t\t\t<AppliIdType>${AppliIdType}</AppliIdType>\n" +
            "\t\t\t\t<AppliIdNo>${AppliIdNo}</AppliIdNo>\n" +
            "\t\t\t\t<AppliIdMobile>${AppliIdMobile}</AppliIdMobile>\n" +
            "\t\t\t\t<SendSMS>Y</SendSMS>\n" +
            "\t\t\t</Applicant>\n" +
            "\t\t\t<Insureds>\n" +
            "\t\t\t\t<Insured>\n" +
            "\t\t\t\t\t<InsuredSeqNo>${InsuredSeqNo}</InsuredSeqNo>\n" +
            "\t\t\t\t\t<InsuredName>${InsuredName}</InsuredName>\n" +
            "\t\t\t\t\t<InsuredIdType>${InsuredIdType}</InsuredIdType>\n" +
            "\t\t\t\t\t<InsuredIdNo>${InsuredIdNo}</InsuredIdNo>\n" +
            "\t\t\t\t\t<InsuredEmail>${InsuredEmail}</InsuredEmail>\n" +
            "\t\t\t\t</Insured>\n" +
            "\t\t\t</Insureds>\n" +
            "\t\t\t<Specials>\n" +
            "\t\t\t\t<Special  key=\"businessDepartmentCode\">3101142701</Special>\n" +
            "\t\t\t</Specials>\n" +
            "\t\t</PolicyInfo>\n" +
            "\t</PolicyInfos>\n" +
            "</ApplyInfo>";

    private String ZCGForm = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>" +
            "<ApplyInfo>\n" +
            "\t<GeneralInfo>\n" +
            "\t\t<UUID>${UUID}</UUID>\n" +
            "\t\t<PlateformCode>CPI000865</PlateformCode>\n" +
            "\t\t<Md5Value>${Md5Value}</Md5Value>\n" +
            "\t</GeneralInfo>\n" +
            "\t<PolicyInfos>\n" +
            "\t\t<PolicyInfo>\n" +
            "\t\t\t<SerialNo>${SerialNo}</SerialNo>\n" +
            "\t\t\t<RiskCode>${RiskCode}</RiskCode>\n" +
            "\t\t\t<OperateTimes>${OperateTimes}</OperateTimes>\n" +
            "\t\t\t<StartDate>${StartDate}</StartDate>\n" +
            "\t\t\t<EndDate>${EndDate}</EndDate>\n" +
            "\t\t\t<StartHour>0</StartHour>\n" +
            "\t\t\t<EndHour>24</EndHour>\n" +
            "\t\t\t<SumAmount>${SumAmount}</SumAmount>\n" +
            "\t\t\t<SumPremium>${SumPremium}</SumPremium>\n" +
            "\t\t\t<ArguSolution>1</ArguSolution>\n" +
            "\t\t\t<Quantity>1</Quantity>\n" +
            "\t\t\t<InsuredPlan>\n" +
            "\t\t\t\t<RationType>${RationType}</RationType>\n" +
            "\t\t\t</InsuredPlan>\n" +
            "\t\t\t<Applicant>\n" +
            "\t\t\t\t<AppliName>${AppliName}</AppliName>\n" +
            "\t\t\t\t<AppliIdType>${AppliIdType}</AppliIdType>\n" +
            "\t\t\t\t<AppliIdNo>${AppliIdNo}</AppliIdNo>\n" +
            "\t\t\t\t<AppliIdMobile>${AppliIdMobile}</AppliIdMobile>\n" +
            "\t\t\t\t<SendSMS>Y</SendSMS>\n" +
            "\t\t\t</Applicant>\n" +
            "\t\t\t<Insureds>\n" +
            "\t\t\t\t<Insured>\n" +
            "\t\t\t\t\t<InsuredSeqNo>${InsuredSeqNo}</InsuredSeqNo>\n" +
            "\t\t\t\t\t<InsuredName>${InsuredName}</InsuredName>\n" +
            "\t\t\t\t\t<InsuredIdType>${InsuredIdType}</InsuredIdType>\n" +
            "\t\t\t\t\t<InsuredIdNo>${InsuredIdNo}</InsuredIdNo>\n" +
            "\t\t\t\t\t<InsuredEmail>${InsuredEmail}</InsuredEmail>\n" +
            "\t\t\t\t</Insured>\n" +
            "\t\t\t</Insureds>\n" +
            "\t\t\t<Specials>\n" +
            "\t\t\t\t<Special  key=\"businessDepartmentCode\">3101142701</Special>\n" +
            "\t\t\t</Specials>\n" +
            "\t\t</PolicyInfo>\n" +
            "\t</PolicyInfos>\n" +
            "</ApplyInfo>";

    private String I9QForm = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>" +
            "<ApplyInfo>\n" +
            "\t<GeneralInfo>\n" +
            "\t\t<UUID>${UUID}</UUID>\n" +
            "\t\t<PlateformCode>CPI000865</PlateformCode>\n" +
            "\t\t<Md5Value>${Md5Value}</Md5Value>\n" +
            "\t</GeneralInfo>\n" +
            "\t<PolicyInfos>\n" +
            "\t\t<PolicyInfo>\n" +
            "\t\t\t<SerialNo>${SerialNo}</SerialNo>\n" +
            "\t\t\t<RiskCode>${RiskCode}</RiskCode>\n" +
            "\t\t\t<OperateTimes>${OperateTimes}</OperateTimes>\n" +
            "\t\t\t<StartDate>${StartDate}</StartDate>\n" +
            "\t\t\t<EndDate>${EndDate}</EndDate>\n" +
            "\t\t\t<StartHour>0</StartHour>\n" +
            "\t\t\t<EndHour>24</EndHour>\n" +
            "\t\t\t<SumAmount>${SumAmount}</SumAmount>\n" +
            "\t\t\t<SumPremium>${SumPremium}</SumPremium>\n" +
            "\t\t\t<ArguSolution>1</ArguSolution>\n" +
            "\t\t\t<Quantity>1</Quantity>\n" +
            "\t\t\t<PayWay>Y</PayWay>\n" +
            "\t\t\t<InsuredPlan>\n" +
            "\t\t\t\t<RationType>${RationType}</RationType>\n" +
            "\t\t\t</InsuredPlan>\n" +
            "\t\t\t<Applicant>\n" +
            "\t\t\t\t<AppliName>${AppliName}</AppliName>\n" +
            "\t\t\t\t<AppliIdType>${AppliIdType}</AppliIdType>\n" +
            "\t\t\t\t<AppliIdNo>${AppliIdNo}</AppliIdNo>\n" +
            "\t\t\t\t<AppliIdMobile>${AppliIdMobile}</AppliIdMobile>\n" +
            "\t\t\t\t<SendSMS>Y</SendSMS>\n" +
            "\t\t\t</Applicant>\n" +
            "\t\t\t<Insureds>\n" +
            "\t\t\t\t<Insured>\n" +
            "\t\t\t\t\t<InsuredSeqNo>${InsuredSeqNo}</InsuredSeqNo>\n" +
            "\t\t\t\t\t<InsuredName>${InsuredName}</InsuredName>\n" +
            "\t\t\t\t\t<InsuredIdType>${InsuredIdType}</InsuredIdType>\n" +
            "\t\t\t\t\t<InsuredIdNo>${InsuredIdNo}</InsuredIdNo>\n" +
            "\t\t\t\t\t<InsuredEmail>${InsuredEmail}</InsuredEmail>\n" +
            "\t\t\t\t</Insured>\n" +
            "\t\t\t</Insureds>\n" +
            //Agricultural节点
            "\t\t\t<Agricultural>\n" +
            "\t\t\t\t<ListType>07</ListType>\n" +
            "\t\t\t\t<SumFamily>1</SumFamily>\n" +
            "\t\t\t\t<gzqydm>上海</gzqydm>\n" +
            "\t\t\t\t<gzqymc>上海</gzqymc>\n" +
            "\t\t\t\t<MarketPrice>00</MarketPrice>\n" +
            "\t\t\t\t<UnitCost>00</UnitCost>\n" +
            "\t\t\t\t<ItemColor>00</ItemColor>\n" +
            "\t\t\t\t<Pregnancy>00</Pregnancy>\n" +
            "\t\t\t\t<Calving>00</Calving>\n" +
            "\t\t\t\t<ItemAge>${ItemAge}</ItemAge>\n" +
            "\t\t\t\t<AgeUnit>1</AgeUnit>\n" +
            "\t\t\t\t<BirthRank>00</BirthRank>\n" +
            "\t\t\t\t<Variety>${Variety}</Variety>\n" +
            "\t\t\t\t<FarmingMethod>01</FarmingMethod>\n" +
            "\t\t\t\t<BatchNo>${BatchNo}</BatchNo>\n" +
            "\t\t\t\t<Unit>09</Unit>\n" +
            "\t\t\t\t<RaiseSite>上海</RaiseSite>\n" +
            "\t\t\t</Agricultural>\n" +
            "\t\t\t<Specials>\n" +
            "\t\t\t\t<Special  key=\"businessDepartmentCode\">3101142701</Special>\n" +
            "\t\t\t</Specials>\n" +
            "\t\t</PolicyInfo>\n" +
            "\t</PolicyInfos>\n" +
            "</ApplyInfo>";

    @Override
    @Transactional
    public ResultResponse generateInsure(InsuranceOrder insuranceOrder) throws IOException {
        String datas = "";
//        Integer insuranceType = insuranceOrder.getInsuranceType();
//        if(insuranceType == 1) {
//            //医疗险
//            datas = getRenderI9QForm(insuranceOrder);
//        } else if(insuranceType == 2) {
//            //家责险
//            datas = getRenderZFOFrom(insuranceOrder);
//        } else if(insuranceType == 3) {
//            //运输险
//            datas = getRenderZCGForm(insuranceOrder);
//        }
        datas = getRenderZCGForm(insuranceOrder);
        System.out.println("报文数据:" + datas);
        EcooperationWebServiceService serviceService = new EcooperationWebServiceService();
        EcooperationWebService service = serviceService.getEcooperationWebServicePort();
        String resp = service.insureService(INSURANCE_SERVICE_NO, datas);
        System.out.println("返回数据:" + resp);
        String errorCode = "";
        String downloadUrl = "";
        try {
            Document document = DocumentHelper.parseText(resp);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator("GeneralInfoReturn"); i.hasNext(); ) {
                Element next = (Element) i.next();
                errorCode = next.elementText("ErrorCode");
                System.out.println("UUID:" + next.elementText("UUID"));
                System.out.println("ErrorCode:" + next.elementText("ErrorCode"));
                System.out.println("ErrorMessage:" + next.elementText("ErrorMessage"));
            }
            for (Iterator i = root.elementIterator("PolicyInfoReturns"); i.hasNext(); ) {
                Element next = (Element) i.next();
                for (Iterator j = next.elementIterator("PolicyInfoReturn"); j
                        .hasNext(); ) {
                    Element e = (Element) j.next();
                    downloadUrl = e.elementText("DownloadUrl");
                    System.out.println("PolicyUrl:" + e.elementText("PolicyUrl"));
                    System.out.println("DownloadUrl:" + e.elementText("DownloadUrl"));
                    System.out.println("SaveResult:" + e.elementText("SaveResult"));
                    System.out.println("SaveMessage:" + e.elementText("SaveMessage"));
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //回调函数
        if (errorCode.equals("00")) {
            //测试
            generatePetupPolicy(null);

//            //投保成功
//            if(insuranceType == 3) {
//                //运输险, 由我们系统生成电子单证
//
//            }

//            savePolicy(insuranceOrder, downloadUrl);//保存电子单证信息
//            successCallBack(insuranceOrder);//更新保单状态信息
            return ResultResponse.createBySuccessMessage("投保成功!");
        } else {
            //投保失败, 就不做详细处理了, 打印出errorCode, 再自己去比对
            System.out.println("ErrorCode:" + errorCode);
            return ResultResponse.createByErrorMessage("投保失败");
        }
    }

    private void savePolicy(InsuranceOrder insuranceOrder, String downloadUrl) throws IOException {
        if (StringUtils.isNotBlank(downloadUrl)) {
//            insuranceOrder.setPolicyDownloadUrl(downloadUrl);
            //根据下载链接, 将图片下载存储到服务器上, 并保存访问url
            RestTemplate rest = new RestTemplate();
            rest.execute(downloadUrl, HttpMethod.GET, (req) -> {
            }, (res) -> {
                InputStream inputStream = res.getBody();
//                FileOutputStream out = new FileOutputStream(POLICY_FOLDER_PREFIX + insuranceOrder.getInsuranceOrderNo() + ".jpg");
                //测试
                FileOutputStream out = new FileOutputStream(POLICY_FOLDER_PREFIX + "test_xxx" + ".pdf");
//                FileOutputStream out = new FileOutputStream("F:/" + "test_xxx" + ".pdf");

                int byteCount = 0;
                while ((byteCount = inputStream.read()) != -1) {
                    out.write(byteCount);
                }
                out.close();
                inputStream.close();

                //保存文件名
//                insuranceOrder.setPolicyImage(insuranceOrder.getInsuranceOrderNo() + ".jpg");
                return null;
            });
        }
    }

    private void generatePetupPolicy(InsuranceOrder insuranceOrder) throws IOException {
        //测试
        if(insuranceOrder == null) {
            insuranceOrder = new InsuranceOrder();
            insuranceOrder.setName("test");
            insuranceOrder.setPhone("test");
            insuranceOrder.setCardType("test");
            insuranceOrder.setCardNo("test");
            insuranceOrder.setOrderNo("test");
            insuranceOrder.setCreateTimeStr(new Date().toString());

        }
        try (InputStream inputStream = new ClassPathResource("/template/pickup_policy.docx").getInputStream()) {
            Map<String, Object> map = new HashMap<>();
            map.put("insuranceOrder", insuranceOrder);
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DocxUtil.processDocxTemplate(inputStream, outputStream, map, null);
                FileOutputStream out = new FileOutputStream("F:/" + "test_xxx" + ".pdf");
                PdfUtil.convertPdf(new ByteArrayInputStream(outputStream.toByteArray()), out);
                outputStream.close();
                out.close();
            } catch (XDocReportException e) {
            }
        }
    }

    private void successCallBack(InsuranceOrder insuranceOrder) {
        insuranceOrder.setApplyTime(new Date());
        insuranceOrder.setStatus(2);//已支付待一级审核
        insuranceOrderRepository.save(insuranceOrder);
    }

    /**
     * 获取家庭险投保报文数据
     *
     * @return
     */
    private String getRenderZFOFrom(InsuranceOrder insuranceOrder) {
        return initFormStr(ZFO_RISK_CODE, ZFO_RATION_TYPE, insuranceOrder);
    }

    /**
     * 获取运输险投保报文数据
     *
     * @return
     */
    private String getRenderZCGForm(InsuranceOrder insuranceOrder) {
        return initFormStr(ZCG_RISK_CODE, ZCG_RATION_TYPE, insuranceOrder);
    }

    /**
     * 获取医疗险投保报文数据
     *
     * @param insuranceOrder
     * @return
     */
    private String getRenderI9QForm(InsuranceOrder insuranceOrder) {
        return initFormStr(I9Q_RISK_CODE, I9Q_RATION_TYPE, insuranceOrder);
    }

    private String initFormStr(String riskCode, String rationType, InsuranceOrder insuranceOrder) {
        //new一个模板资源加载器
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        /* 使用Beetl默认的配置。
         * Beetl可以使用配置文件的方式去配置，但由于此处是直接上手的例子，
         * 我们不去管配置的问题，只需要基本的默认配置就可以了。
         */
        Configuration config = null;
        try {
            config = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Beetl的核心GroupTemplate
        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, config);
        //我们自定义的模板，其中${title}就Beetl默认的占位符
        Template template = groupTemplate.getTemplate(ZCGForm);
//        Template template = groupTemplate.getTemplate(ZFOForm);
//        Template template = groupTemplate.getTemplate(I9QForm);

        //保险信息
        template.binding("UUID", "iiiiiiiiqQpufatesQt00334");

        ////////测试参数
        template.binding("SerialNo", "1");
        template.binding("RiskCode", riskCode);
        template.binding("OperateTimes", "2019-09-02 14:41:40");//下单时间
        template.binding("StartDate", "2019-09-02");//起保时间
        template.binding("EndDate", "2020-09-01");//终保时间
        template.binding("SumAmount", "10000.00");//保额
        template.binding("SumPremium", "1.00");//保费
        //测试key
        template.binding("Md5Value", generateMD5SecretKey("iiiiiiiiqQpufatesQt00334", "1.00", "Picc37mu63ht38mw"));
        //投保人、被保人信息
        template.binding("RationType", rationType);
        template.binding("AppliName", "testxxx");//投保人姓名
        template.binding("AppliIdType", "01");//投保人证件类型
        template.binding("AppliIdNo", "430381198707230426");
        template.binding("AppliIdMobile", "18715637638");
        template.binding("InsuredSeqNo", "1");
        template.binding("InsuredName", "testxxx");//被保人姓名
        template.binding("InsuredIdType", "02");//被保人证件类型
        template.binding("InsuredIdNo", "342501199109126038");
        template.binding("InsuredEmail", "1092347670@qq.com");
//额外字段-医疗险字段
//        template.binding("ItemAge", "01");
//        template.binding("Variety", "边境牧羊犬");
//        template.binding("BatchNo", "0001");

        //生产环境参数
//        String uuid = generateUUID(insuranceOrder.getInsuranceOrderNo());
//        template.binding("UUID", uuid);
//        template.binding("OperateTimes", insuranceOrder.getApplyTime().toString());//下单时间
//        template.binding("StartDate", insuranceOrder.getInsuranceEffectTime().toString());//起保时间
//        template.binding("EndDate", "2020-09-01");//终保时间
//        template.binding("SumAmount", insuranceOrder.getSumAmount().toString());//保额
//        template.binding("SumPremium", insuranceOrder.getSumPremium().toString());//保费
//        template.binding("Md5Value", generateMD5SecretKey(uuid, insuranceOrder.getSumPremium().toString(), SECRET_KEY));
//        template.binding("RationType", rationType);
//        template.binding("AppliName", insuranceOrder.getName());//投保人姓名
//        template.binding("AppliIdType", String.valueOf(insuranceOrder.getCardType()));//投保人证件类型
//        template.binding("AppliIdNo", insuranceOrder.getCardNo());
//        template.binding("AppliIdMobile", insuranceOrder.getPhone());
//        template.binding("InsuredSeqNo", insuranceOrder.getAcceptSeqNo());
//        template.binding("InsuredName", insuranceOrder.getAcceptName());//被保人姓名
//        template.binding("InsuredIdType", insuranceOrder.getAcceptCardType());//被保人证件类型
//        template.binding("InsuredIdNo", insuranceOrder.getAcceptCardNo());
//        template.binding("InsuredEmail", insuranceOrder.getAcceptMail());
        //额外字段-医疗险字段
//        Integer petCardId = insuranceOrder.getPetCardId();
//        PetCard petCard = petCardRepository.findById(petCardId).orElse(null);
//        template.binding("ItemAge", String.valueOf(petCard.getAge()));//宠物年龄
//        template.binding("Variety", petCard.getBreed());//宠物品种
//        Integer medicalInsuranceShopChipId = insuranceOrder.getMedicalInsuranceShopChipId();
//        InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(medicalInsuranceShopChipId).orElse(null);
//        template.binding("BatchNo", insuranceShopChip.getCore());//宠物芯片代码

        //渲染字符串
        String str = template.render();
        System.out.println(str);
        return str;
    }

    /**
     * 生成UUID
     *
     * @return
     */
    private String generateUUID(String orderNo) {
        return COMPANY_CODE + orderNo;
    }

    /**
     * 生成MD5秘钥
     *
     * @param uuid
     * @param SumPremium
     * @param secretKey
     * @return
     */
    private String generateMD5SecretKey(String uuid, String SumPremium, String secretKey) {
        try {
            return Md5.encodeByMd5(uuid + SumPremium + secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
