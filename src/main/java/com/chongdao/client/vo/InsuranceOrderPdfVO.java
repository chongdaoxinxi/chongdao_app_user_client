package com.chongdao.client.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/28
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class InsuranceOrderPdfVO {
    private String policyNo;
    private String name;
    private String phone;
    private String cardType;
    private String cardNo;
    private String petName;
    private String typeName;
    private String birthDate;
    private String age;
    private String petCardType;
    private String petCardNo;
    private String orderNo;
    private String createDate;
}
