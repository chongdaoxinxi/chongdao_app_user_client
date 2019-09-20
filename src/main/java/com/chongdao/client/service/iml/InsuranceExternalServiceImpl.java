package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.webservice.EcooperationWebService;
import com.chongdao.client.service.insurance.webservice.EcooperationWebServiceService;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.utils.DocxUtil;
import com.chongdao.client.utils.Md5;
import com.chongdao.client.utils.PdfUtil;
import com.chongdao.client.vo.InsuranceOrderPdfVO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

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
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    private static final String INVOICE_URL = "http://partnertest.mypicc.com.cn/ecooperation/InvoiceConfigController/StartInvoiceConfig.do";//请求电子发票接口
    private static final String INSURANCE_URL = "http://partnertest.mypicc.com.cn/ecooperation/webservice/insure?wsdl";//投保接口
    private static final String PLATE_FORM_CODE = "CPI000865";//平台代码(保险公司提供)
    private static final String SECRET_KEY = "Picc37mu63ht38mw";//秘钥(保险公司提供)
    private static final String INVOICE_TITLE= "XXX";//电子发票抬头
    private static final String INSURANCE_SERVICE_NO = "001001";
    private static final String ZFO_RISK_CODE = "ZFO";
    private static final String ZCG_RISK_CODE = "ZCG";
    private static final String I9Q_RISK_CODE = "I9Q";
    private static final String POLICY_FOLDER_PREFIX = "../../policy/";//电子单证本地保存地址
    private static final String POLICY_REALPATH = "/home/policy/";
    private static final String INVOICE_FOLDER_PREFIX = "../../invoice/";//电子发票本地保存地址
    private static final String INVOICE_REALPATH = "/home/invoice/";

    private String ZFOForm = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>" +
            "<ApplyInfo>\n" +
            "\t<GeneralInfo>\n" +
            "\t\t<UUID>${UUID}</UUID>\n" +
            "\t\t<PlateformCode>${PlateformCode}</PlateformCode>\n" +
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
            "\t\t\t\t<AppliIdEmail>${AppliIdEmail}</AppliIdEmail>\n" +
            "\t\t\t\t<AppliAddress>${AppliAddress}</AppliAddress>\n" +
            "\t\t\t\t<AppliIdentity>${AppliIdentity}</AppliIdentity>\n" +
            "\t\t\t\t<SendSMS>Y</SendSMS>\n" +
            "\t\t\t</Applicant>\n" +
            "\t\t\t<Insureds>\n" +
            "\t\t\t\t<Insured>\n" +
            "\t\t\t\t\t<InsuredSeqNo>${InsuredSeqNo}</InsuredSeqNo>\n" +
            "\t\t\t\t\t<InsuredName>${InsuredName}</InsuredName>\n" +
            "\t\t\t\t\t<InsuredIdType>${InsuredIdType}</InsuredIdType>\n" +
            "\t\t\t\t\t<InsuredIdNo>${InsuredIdNo}</InsuredIdNo>\n" +
            "\t\t\t\t\t<InsuredAddress>${InsuredAddress}</InsuredAddress>\n" +
            "\t\t\t\t\t<InsuredIdMobile>${InsuredIdMobile}</InsuredIdMobile>\n" +
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
            "\t\t<PlateformCode>${PlateformCode}</PlateformCode>\n" +
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
            "\t\t<PlateformCode>${PlateformCode}</PlateformCode>\n" +
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
            "\t\t\t\t<AppliIdEmail>${AppliIdEmail}</AppliIdEmail>\n" +
            "\t\t\t\t<AppliAddress>${AppliAddress}</AppliAddress>\n" +
            "\t\t\t\t<AppliIdentity>${AppliIdentity}</AppliIdentity>\n" +
            "\t\t\t\t<SendSMS>Y</SendSMS>\n" +
            "\t\t\t</Applicant>\n" +
            "\t\t\t<Insureds>\n" +
            "\t\t\t\t<Insured>\n" +
            "\t\t\t\t\t<InsuredSeqNo>${InsuredSeqNo}</InsuredSeqNo>\n" +
            "\t\t\t\t\t<InsuredName>${InsuredName}</InsuredName>\n" +
            "\t\t\t\t\t<InsuredIdType>${InsuredIdType}</InsuredIdType>\n" +
            "\t\t\t\t\t<InsuredIdNo>${InsuredIdNo}</InsuredIdNo>\n" +
            "\t\t\t\t\t<InsuredAddress>${InsuredAddress}</InsuredAddress>\n" +
            "\t\t\t\t\t<InsuredIdMobile>${InsuredIdMobile}</InsuredIdMobile>\n" +
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
            "\t\t\t\t<AgeUnit>01</AgeUnit>\n" +
            "\t\t\t\t<BirthRank>00</BirthRank>\n" +
            "\t\t\t\t<Variety>${Variety}</Variety>\n" +
            "\t\t\t\t<PetName>${PetName}</PetName>\n" +
            "\t\t\t\t<FarmingMethod>1</FarmingMethod>\n" +
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
        Integer insuranceType = insuranceOrder.getInsuranceType();
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
        if (insuranceType == 1) {
            //医疗险
            Template template = groupTemplate.getTemplate(I9QForm);
            datas = getRenderI9QForm(insuranceOrder, template);
        } else if (insuranceType == 2) {
            //家责险
            Template template = groupTemplate.getTemplate(ZFOForm);
            datas = getRenderZFOFrom(insuranceOrder, template);
        } else if (insuranceType == 3) {
            //运输险
            Template template = groupTemplate.getTemplate(ZCGForm);
            datas = getRenderZCGForm(insuranceOrder, template);
        }
        System.out.println("报文数据:" + datas);
        EcooperationWebServiceService serviceService = new EcooperationWebServiceService();
        EcooperationWebService service = serviceService.getEcooperationWebServicePort();
        String resp = service.insureService(INSURANCE_SERVICE_NO, datas);
        System.out.println("返回数据:" + resp);

        String errorCode = "";
        String downloadUrl = "";
        String payUrl = "";
        String proposalNo = "";
        String policyNo = "";
        String saveResult = "";
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
                    proposalNo = e.elementText("ProposalNo");
                    policyNo = e.elementText("PolicyNo");
                    saveResult = e.elementText("SaveResult");
                    System.out.println("PolicyUrl:" + e.elementText("PolicyUrl"));
                    System.out.println("DownloadUrl:" + e.elementText("DownloadUrl"));
                    System.out.println("SaveResult:" + e.elementText("SaveResult"));
                    System.out.println("SaveMessage:" + e.elementText("SaveMessage"));

                    for (Iterator k = e.elementIterator("PayOnlineInfo"); ((Iterator) k).hasNext(); ) {
                        Element e1 = (Element) k.next();
                        payUrl = e1.elementText("PayURL");
                        payUrl = payUrl.replaceAll("\n", "");
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

//        if (errorCode.equals("00") && saveResult.equals("00")) {
        if (saveResult.equals("00")) { //just for test
            if (insuranceType == 3) {
                return successCallBackOperation(insuranceOrder, downloadUrl, policyNo);
            } else {
                //对于见费出单的保险, 需要保存预下单号
                insuranceOrder.setProposalNo(proposalNo);
                insuranceOrder.setStatus(1);//将订单状态设为待支付
                insuranceOrderRepository.save(insuranceOrder);
                System.out.println("支付链接:" + payUrl);
                return ResultResponse.createBySuccess("预下单成功, 返回支付链接", payUrl);
            }
        } else {
            //投保失败, 就不做详细处理了, 打印出errorCode, 再自己去比对
            System.out.println("ErrorCode:" + errorCode);
            System.out.println("SaveResult:" + saveResult);
            return ResultResponse.createByErrorMessage("投保失败");
        }
    }

    @Override
    public ResultResponse payCallBackManage(String payCallBackInfo) throws IOException {
        String downloadUrl = "";
        String uuid = "";
        String proposalNo = "";
        String policyNo = "";
        System.out.println("我进入了支付回调处理方法!");
        try {
            Document document = DocumentHelper.parseText(payCallBackInfo);
            Element root = document.getRootElement();
            for (Iterator j = root.elementIterator("GeneralInfo"); j.hasNext(); ) {
                Element generalInfo = (Element) j.next();
                uuid = generalInfo.elementText("UUID");
                System.out.println("UUID:" + generalInfo.elementText("UUID"));
                System.out.println("ErrorCode:" + generalInfo.elementText("ErrorCode"));
                System.out.println("ErrorMessage:" + generalInfo.elementText("ErrorMessage"));
            }
            for (Iterator k = root.elementIterator("PayOnlineInfo"); k.hasNext(); ) {
                Element payOnlineInfo = (Element) k.next();
                downloadUrl = payOnlineInfo.elementText("Downloadurl");
                proposalNo = payOnlineInfo.elementText("Proposalno");
                policyNo = payOnlineInfo.elementText("PolicyNo");
                System.out.println("Downloadurl:" + payOnlineInfo.elementText("Downloadurl"));
                System.out.println("Proposalno:" + payOnlineInfo.elementText("Proposalno"));
                System.out.println("PolicyNo:" + payOnlineInfo.elementText("PolicyNo"));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(proposalNo)) {
            List<InsuranceOrder> ioList = insuranceOrderRepository.findByProposalNo(proposalNo);
            if (ioList != null && ioList.size() > 0) {
                return successCallBackOperation(ioList.get(0), downloadUrl, policyNo);
            }
        }
        return ResultResponse.createByErrorMessage("投保失败, 通过返回的UUID找不到对应的保险订单");
    }

    @Override
    public ResultResponse requestInvoiceInfo(Integer insuranceOrderId) {
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        if(insuranceOrder == null) {
            return ResultResponse.createByErrorMessage("无效的保险订单ID!");
        }
        Document document = DocumentHelper.createDocument();
        Element applyInfo = document.addElement("ApplyInfo");

        Element generalInfo = applyInfo.addElement("GeneralInfo");
        //测试数据
        String uuid = insuranceOrder.getInsuranceOrderNo();
        generalInfo.addElement("UUID").setText(uuid);
        generalInfo.addElement("PlateformCode").setText(PLATE_FORM_CODE);
        generalInfo.addElement("Md5Value").setText(generateInvoiceMD5SecretKey(uuid, SECRET_KEY));

        Element invoiceInfo = applyInfo.addElement("InvoiceInfo");
        invoiceInfo.addElement("Policyno").setText(insuranceOrder.getPolicyNo());//投保单号
        invoiceInfo.addElement("RequestTime").setText(DateTimeUtil.dateToStr(new Date()));
        invoiceInfo.addElement("InvoiceTitle").setText(INVOICE_TITLE);//发票抬头
        invoiceInfo.addElement("Phone").setText(insuranceOrder.getPhone());
        String address = insuranceOrder.getAddress();
        if(address == null) {
            address = "";
        }
        invoiceInfo.addElement("AddressAndPhone").setText(address);//地址+电话
        String bankCardNo = insuranceOrder.getBankCardNo();
        if(bankCardNo == null) {
            bankCardNo = "";
        }
        invoiceInfo.addElement("BankAccount").setText(bankCardNo);//开户行+账号
        invoiceInfo.addElement("BuyerTaxpayerIdentifyNumber").setText("");//纳税人识别号
        String email = insuranceOrder.getEmail();
        if(email == null) {
            email = "";
        }
        invoiceInfo.addElement("Email").setText(email);
        invoiceInfo.addElement("Type").setText("2");//1 短链接, 2 版式下载

        String text = document.asXML();
        try {
            //Http协议调用接口，其中serviceBusURI是要调用地址
            URL url = new URL(INVOICE_URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            //设置编码，不然返回报文格式乱码
            con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(text.getBytes("UTF-8")));
            out.flush();
            out.close();

            //返回数据
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "GB2312"));
            String line = "";
            //存放请求内容
            StringBuffer xml = new StringBuffer();
            while ((line = br.readLine()) != null) {
                xml.append(line);
            }
            System.out.println(xml.toString());
            //解析报文，字符串转XML
            Document doc = DocumentHelper.parseText(xml.toString());
            Element root = doc.getRootElement();
            Element errorCode = root.element("GeneralInfoReturn").element("ErrorCode");
            String invoiceDownloadUrl = root.element("PolicyInfoReturn").elementText("Url");
            System.out.println(errorCode.getText());
            System.out.println(invoiceDownloadUrl);
            saveInvoicePdf(insuranceOrder, invoiceDownloadUrl);
            return ResultResponse.createBySuccess(invoiceDownloadUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存电子发票文件至本地
     */
    private void saveInvoicePdf(InsuranceOrder insuranceOrder, String downloadUrl) {
        if (StringUtils.isNotBlank(downloadUrl)) {
            insuranceOrder.setInvoiceDownloadUrl(downloadUrl);
            //根据下载链接, 将图片下载存储到服务器上, 并保存访问url
            RestTemplate rest = new RestTemplate();
            rest.execute(downloadUrl, HttpMethod.GET, (req) -> {
            }, (res) -> {
                InputStream inputStream = res.getBody();
                FileOutputStream out = new FileOutputStream(INVOICE_FOLDER_PREFIX + insuranceOrder.getPolicyNo() + ".pdf");
                int byteCount = 0;
                while ((byteCount = inputStream.read()) != -1) {
                    out.write(byteCount);
                }
                out.close();
                inputStream.close();

//                保存文件名
                insuranceOrder.setInvoiceImage(insuranceOrder.getPolicyNo() + ".pdf");
                return null;
            });
        }
    }

    /**
     * 成功投保回调逻辑
     */
    private ResultResponse successCallBackOperation(InsuranceOrder insuranceOrder, String downloadUrl, String policyNo) throws IOException {
//        //投保成功
        Integer insuranceType = insuranceOrder.getInsuranceType();
        if (insuranceType == 3) {
            //运输险, 由我们系统生成电子单证
            //测试, 生成运输险的电子单证
            //保存我们生成的电子单证的访问地址和下载链接
            insuranceOrder.setPolicyNo(policyNo);
            generatePetupPolicy(insuranceOrder);
            savePolicy(insuranceOrder, downloadUrl);//保存电子单证信息
            updateInsuranceOrderStatus(insuranceOrder);//更新保单状态信息
        } else {
            insuranceOrder.setPolicyNo(policyNo);//保存电子单证号
            savePolicy(insuranceOrder, downloadUrl);//保存电子单证信息
            //对于见费出单的, 将电子单证和电子单证下载下来的图片保存到新字段
            insuranceOrder.setPolicyCdxxDownloadUrl(insuranceOrder.getPolicyDownloadUrl());
            insuranceOrder.setPolicyCdxxImage(insuranceOrder.getPolicyImage());
            updateInsuranceOrderStatus(insuranceOrder);//更新保单状态信息
        }
        System.out.println("电子单证下载链接:" + downloadUrl);
        return ResultResponse.createBySuccessMessage("投保成功!");
    }

    /**
     * 保存运输险订单金额至地区管理员账户
     *
     * @param insuranceOrder
     */
    private void savePickupInsuranceOrderMoney(InsuranceOrder insuranceOrder) {
        BigDecimal sumPremium = insuranceOrder.getSumPremium();
        String orderNo = insuranceOrder.getOrderNo();
        OrderInfo orderInfo = orderInfoRepository.findByOrderNo(orderNo);
        String areaCode = orderInfo.getAreaCode();
        List<Management> managementList = managementRepository.findByAreaCodeAndStatus(areaCode, 1);//查询出地区管理员帐号
        if (managementList.size() > 0) {
            Management management = managementList.get(0);
            BigDecimal money = management.getMoney();
            if (money == null) {
                money = new BigDecimal(0);
            }
            management.setMoney(money.add(sumPremium));
            managementRepository.save(management);
        }
    }

    /**
     * 保存电子单证至本地服务器(医疗险和家责险)
     *
     * @param insuranceOrder
     * @param downloadUrl
     * @throws IOException
     */
    private void savePolicy(InsuranceOrder insuranceOrder, String downloadUrl) throws IOException {
        if (StringUtils.isNotBlank(downloadUrl)) {
            insuranceOrder.setPolicyDownloadUrl(downloadUrl);
            //根据下载链接, 将图片下载存储到服务器上, 并保存访问url
            RestTemplate rest = new RestTemplate();
            rest.execute(downloadUrl, HttpMethod.GET, (req) -> {
            }, (res) -> {
                InputStream inputStream = res.getBody();
                FileOutputStream out = new FileOutputStream(POLICY_FOLDER_PREFIX + insuranceOrder.getPolicyNo() + ".pdf");
//                //测试
//                FileOutputStream out = new FileOutputStream(POLICY_FOLDER_PREFIX + "test_xxx" + ".pdf");
//                FileOutputStream out = new FileOutputStream("F:/" + "test_xxx" + ".pdf");

                int byteCount = 0;
                while ((byteCount = inputStream.read()) != -1) {
                    out.write(byteCount);
                }
                out.close();
                inputStream.close();

//                保存文件名
                insuranceOrder.setPolicyImage(insuranceOrder.getPolicyNo() + ".pdf");
                return null;
            });
        }
    }

    /**
     * 生成运输险电子单证
     *
     * @param insuranceOrder
     * @throws IOException
     */
    private void generatePetupPolicy(InsuranceOrder insuranceOrder) throws IOException {
        InsuranceOrderPdfVO pdfVo = new InsuranceOrderPdfVO();
        //测试
        if (insuranceOrder == null) {
            insuranceOrder = new InsuranceOrder();
            insuranceOrder.setName("test");
            insuranceOrder.setPhone("test");
            insuranceOrder.setCardType("test");
            insuranceOrder.setCardNo("test");
            insuranceOrder.setOrderNo("test");
            BeanUtils.copyProperties(insuranceOrder, pdfVo);
            pdfVo.setPolicyNo("test");
            pdfVo.setPetName("test");
            pdfVo.setTypeName("test");
            pdfVo.setBirthDate("test");
            pdfVo.setAge("1");
            pdfVo.setPetCardType("test");
            pdfVo.setPetCardNo("test");
            pdfVo.setCreateDate(DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        } else {
            BeanUtils.copyProperties(insuranceOrder, pdfVo);
            Integer petCardId = insuranceOrder.getPetCardId();
            PetCard petCard = petCardRepository.findById(petCardId).orElse(null);
            if (petCard != null) {
                pdfVo.setPolicyNo(pdfVo.getPolicyNo());
                pdfVo.setPetName(petCard.getName());
                pdfVo.setTypeName(petCard.getTypeName());
                pdfVo.setBirthDate(DateTimeUtil.dateToStr(petCard.getBirthDate(), "yyyy-MM-dd"));
                pdfVo.setAge(petCard.getAge() + "");
                pdfVo.setPetCardType("test");//这里数据还未处理
                pdfVo.setPetCardNo("test");//这里数据还未处理
                pdfVo.setCreateDate(DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
            }
        }
        try (InputStream inputStream = new ClassPathResource("/template/pickup_policy.docx").getInputStream()) {
            Map<String, Object> map = new HashMap<>();
            map.put("pdfVo", pdfVo);
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DocxUtil.processDocxTemplate(inputStream, outputStream, map, null);
                //生产环境
                FileOutputStream out = new FileOutputStream(POLICY_FOLDER_PREFIX + pdfVo.getPolicyNo() + "_cdxx" + ".pdf");
                //本地测试环境
//                FileOutputStream out = new FileOutputStream("F:/" + pdfVo.getPolicyNo() + ".pdf");

                PdfUtil.convertPdf(new ByteArrayInputStream(outputStream.toByteArray()), out);
                outputStream.close();
                out.close();
                insuranceOrder.setPolicyCdxxImage(pdfVo.getPolicyNo() + "_cdxx" + ".pdf");
            } catch (XDocReportException e) {
            }
        }
    }

    /**
     * 更新保险订单状态
     *
     * @param insuranceOrder
     */
    private void updateInsuranceOrderStatus(InsuranceOrder insuranceOrder) {
        insuranceOrder.setApplyTime(new Date());
        insuranceOrder.setStatus(2);//已支付进入等待期
        insuranceOrderRepository.save(insuranceOrder);
    }

    /**
     * 获取家庭险投保报文数据
     *
     * @return
     */
    private String getRenderZFOFrom(InsuranceOrder insuranceOrder, Template template) {
        return initFormStr(ZFO_RISK_CODE, insuranceOrder.getRationType(), insuranceOrder, template);
    }

    /**
     * 获取运输险投保报文数据
     *
     * @return
     */
    private String getRenderZCGForm(InsuranceOrder insuranceOrder, Template template) {
        return initFormStr(ZCG_RISK_CODE, insuranceOrder.getRationType(), insuranceOrder, template);
    }

    /**
     * 获取医疗险投保报文数据
     *
     * @param insuranceOrder
     * @return
     */
    private String getRenderI9QForm(InsuranceOrder insuranceOrder, Template template) {
        return initFormStr(I9Q_RISK_CODE, insuranceOrder.getRationType(), insuranceOrder, template);
    }

    /**
     * 渲染投保报文
     *
     * @param riskCode
     * @param rationType
     * @param insuranceOrder
     * @param template
     * @return
     */
    private String initFormStr(String riskCode, String rationType, InsuranceOrder insuranceOrder, Template template) {
        //生产环境参数
        String uuid = insuranceOrder.getInsuranceOrderNo();
        template.binding("UUID", uuid);
        template.binding("PlateformCode", PLATE_FORM_CODE);
        template.binding("SerialNo", "1");
        template.binding("RiskCode", riskCode);
        template.binding("OperateTimes", DateTimeUtil.dateToStr(insuranceOrder.getApplyTime(), "yyyy-MM-dd hh:mm:ss"));//下单时间
        template.binding("StartDate", DateTimeUtil.dateToStr(insuranceOrder.getInsuranceEffectTime(), "yyyy-MM-dd"));//起保时间
        template.binding("EndDate", DateTimeUtil.dateToStr(insuranceOrder.getInsuranceFailureTime(), "yyyy-MM-dd"));//终保时间
        template.binding("SumAmount", insuranceOrder.getSumAmount().toString());//保额
        template.binding("SumPremium", insuranceOrder.getSumPremium().toString());//保费
        template.binding("Md5Value", generateInsuranceMD5SecretKey(uuid, insuranceOrder.getSumPremium().toString(), SECRET_KEY));
        template.binding("RationType", rationType);
        template.binding("AppliName", insuranceOrder.getName());
        template.binding("AppliIdType", String.valueOf(insuranceOrder.getCardType()));
        template.binding("AppliIdNo", insuranceOrder.getCardNo());
        template.binding("AppliIdMobile", insuranceOrder.getPhone());
        template.binding("AppliIdEmail", insuranceOrder.getEmail());
        template.binding("AppliAddress", insuranceOrder.getAddress());
        template.binding("AppliIdentity", insuranceOrder.getBeneficiary());
        template.binding("InsuredSeqNo", insuranceOrder.getAcceptSeqNo());
        template.binding("InsuredName", insuranceOrder.getAcceptName());
        template.binding("InsuredIdType", insuranceOrder.getAcceptCardType());
        template.binding("InsuredIdNo", insuranceOrder.getAcceptCardNo());
        template.binding("InsuredAddress", insuranceOrder.getAcceptAddress());
        template.binding("InsuredIdMobile", insuranceOrder.getAcceptPhone());
        template.binding("InsuredEmail", insuranceOrder.getAcceptMail());
//        额外字段-医疗险字段
        if (insuranceOrder.getInsuranceType() == 1) {
            // 由于H5投保放弃了宠物卡片, 所以此处代码暂时注销
//            Integer petCardId = insuranceOrder.getPetCardId();
//            PetCard petCard = petCardRepository.findById(petCardId).orElse(null);
//            template.binding("ItemAge", String.valueOf(petCard.getAge()));//宠物年龄
//            template.binding("Variety", petCard.getBreed());//宠物品种
            template.binding("ItemAge", insuranceOrder.getPetAge());
            template.binding("Variety", insuranceOrder.getPetBreedName());
            template.binding("PetName", insuranceOrder.getPetName());
            Integer medicalInsuranceShopChipId = insuranceOrder.getMedicalInsuranceShopChipId();
            if(medicalInsuranceShopChipId != null) {
                InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(medicalInsuranceShopChipId).orElse(null);
                template.binding("BatchNo", insuranceShopChip.getCore());//宠物芯片代码
            } else {
                template.binding("BatchNo", insuranceOrder.getShopChipCode());
            }
        }

        //渲染字符串
        String str = template.render();
        System.out.println(str);
        return str;
    }

    /**
     * 生成投保MD5秘钥
     *
     * @param uuid
     * @param SumPremium
     * @param secretKey
     * @return
     */
    private String generateInsuranceMD5SecretKey(String uuid, String SumPremium, String secretKey) {
        try {
            return Md5.encodeByMd5(uuid + SumPremium + secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成请求电子发票的MD5秘钥
     * @param uuid
     * @param secretKey
     * @return
     */
    private String generateInvoiceMD5SecretKey(String uuid, String secretKey) {
        try {
            return Md5.encodeByMd5(uuid + secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
