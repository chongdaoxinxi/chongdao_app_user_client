package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.webservice.EcooperationWebService;
import com.chongdao.client.service.insurance.webservice.EcooperationWebServiceService;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/31
 * @Version 1.0
 **/
@Service
public class InsuranceExternalServiceImpl implements InsuranceExternalService {
    private static final String INSURANCE_URL = "http://partnertest.mypicc.com.cn/ecooperation/webservice/insure?wsdl";
    private static final String INSURANCE_SERVICE_NO = "001001";
    private static final String ZFO_RISK_CODE = "ZFO";
    private static final String ZFO_RATION_TYPE = "ZFO310000a";
    private static final String ZCG_RISK_CODE = "ZCG";
    private static final String ZCG_RATION_TYPE = "ZCG3199001";

    private String ZFOForm = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>" +
            "<ApplyInfo>\n" +
            "\t<GeneralInfo>\n" +
            "\t\t<UUID>${UUID}</UUID>\n" +
            "\t\t<PlateformCode>CPI000865</PlateformCode>\n" +
            "\t\t<Md5Value>136a5b8615eba48842d8cc62bb134c56</Md5Value>\n" +
            "\t</GeneralInfo>\n" +
            "\t<PolicyInfos>\n" +
            "\t\t<PolicyInfo>\n" +
            "\t\t\t<SerialNo>${SerialNo}</SerialNo>\n" +
            "\t\t\t<RiskCode>ZFO</RiskCode>\n" +
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
            "\t\t\t\t<AppliIdType>${AppliIdType}</AppliIdType>\n"+
            "\t\t\t\t<AppliIdNo>${AppliIdNo}</AppliIdNo>\n"+
            "\t\t\t\t<AppliIdMobile>${AppliIdMobile}</AppliIdMobile>\n"+
            "\t\t\t\t<SendSMS>Y</SendSMS>\n" +
            "\t\t\t</Applicant>\n" +
            "\t\t\t<Insureds>\n" +
            "\t\t\t\t<Insured>\n" +
            "\t\t\t\t\t<InsuredSeqNo>${InsuredSeqNo}</InsuredSeqNo>\n" +
            "\t\t\t\t\t<InsuredName>${InsuredName}</InsuredName>\n"+
            "\t\t\t\t\t<InsuredIdType>${InsuredIdType}</InsuredIdType>\n"+
            "\t\t\t\t\t<InsuredIdNo>${InsuredIdNo}</InsuredIdNo>\n"+
            "\t\t\t\t\t<InsuredEmail>${InsuredEmail}</InsuredEmail>\n" +
            "\t\t\t\t</Insured>\n" +
            "\t\t\t</Insureds>\n" +
            "\t\t\t<Specials>\n" +
            "\t\t\t\t<Special  key=\"businessDepartmentCode\">3101142701</Special>\n" +
            "\t\t\t</Specials>\n" +
            "\t\t</PolicyInfo>\n" +
            "\t</PolicyInfos>\n" +
            "</ApplyInfo>";

    @Override
    public ResultResponse generateInsure() {
//        String datas = getRenderZFOFrom();
        String datas = getRenderZCGForm();
        System.out.println("报文数据:" + datas);
        EcooperationWebServiceService serviceService = new EcooperationWebServiceService();
        EcooperationWebService service = serviceService.getEcooperationWebServicePort();
        String s = service.insureService(INSURANCE_SERVICE_NO, datas);
        System.out.println("返回数据:" + s);
        return null;
    }

    /**
     * 获取家庭险投保报文数据
     * @return
     */
    private String getRenderZFOFrom() {
       return initFormStr(ZFO_RISK_CODE, ZFO_RATION_TYPE);
    }

    /**
     * 获取运输险投保报文数据
     * @return
     */
    private String getRenderZCGForm() {
        return initFormStr(ZCG_RISK_CODE, ZCG_RATION_TYPE);
    }

    private String initFormStr(String riskCode, String rationType) {
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
        Template template = groupTemplate.getTemplate(ZFOForm);
        template.binding("UUID","qQpufatesQt00122");
        template.binding("SerialNo", "1");
        template.binding("RiskCode", riskCode);
        template.binding("OperateTimes", "2019-08-02 14:41:40");
        template.binding("StartDate", "2019-08-02");
        template.binding("EndDate", "2020-08-02");
        template.binding("SumAmount", "500000.00");
        template.binding("SumPremium", "270.00");
        template.binding("RationType", rationType);
        template.binding("AppliName", "testxxx");
        template.binding("AppliIdType", "01");
        template.binding("AppliIdNo", "430381198707230426");
        template.binding("AppliIdMobile", "17631088624");
        template.binding("InsuredSeqNo", "1");
        template.binding("InsuredName", "testxxx");
        template.binding("InsuredIdType", "02");
        template.binding("InsuredIdNo", "110101198001030Q");
        template.binding("InsuredEmail", "zhangxiaozhao@sinosoft.com.cn");
        //渲染字符串
        String str = template.render();
        System.out.println(str);
        return str;
    }
}
