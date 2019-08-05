package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 医疗险被保人
 * @Author onlineS
 * @Date 2019/5/28
 * @Version 1.0
 **/
@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicalInsuranceRecognizee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String cardType;//01:身份证;02:户口本;03:护照;04:军人证件;05:驾驶执照;06:返乡证;07:港澳身份证;08:工号;09:赴台通行证;10:港澳通行证;15:士兵证;16:外国人永久通行证;25:港澳居民来往内地通行证;26:台湾居民来往内地通行证;31:组织机构代码;37:统一社会信用代码;99:其他;
    private String cardNo;
    private String mail;
    private Date createTime;
}
