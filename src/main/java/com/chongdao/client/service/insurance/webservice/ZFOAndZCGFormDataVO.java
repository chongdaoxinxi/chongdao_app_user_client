package com.chongdao.client.service.insurance.webservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/2
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
public class ZFOAndZCGFormDataVO {
    private String uuid;//公司名称缩写+订单号
    private String plateFormCode;//平台项目标识
    private String md5Value;//密钥, PICC提供
    private String serialNo;//请求序列号
    private String riskCode;//险种代码, PICC提供
    private String operateTimes;//售出保险时间
    private String startDate;//起保时间
    private String endDate;//终保时间
    private String startHour;//起保小时, 默认0
    private String endOur;//终保小时, 默认24
    private String sumAmount;//保单总保金额
    private String sumPremium;//保单总保险费-用户付费
    private String arguSolution;//争议解决方式, 默认为1, 代表诉讼
    private String rationType;//方案代码, PICC提供
    private String appliName;//投保人姓名
    private String appliIdType;//投保人证件类型
    private String appliIdNo;//投保人证件号
    private String appliIdMobile;//投保人手机
    private String sendSMS;//
    private String insuredSeqNo;//被保人序列号
    private String insuredName;//被保人姓名
    private String insuredIdType;//被保人证件类型
    private String insuredIdNo;//被保人正面号
    private String insuredEmail;//被保人邮箱
}
