
package com.chongdao.client.service.insurance.webservicedev;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>insureService complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="insureService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="interfaceNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insureService", propOrder = {
    "interfaceNo",
    "datas"
})
public class InsureService {

    protected String interfaceNo;
    protected String datas;

    /**
     * ???interfaceNo????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceNo() {
        return interfaceNo;
    }

    /**
     * ????interfaceNo????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceNo(String value) {
        this.interfaceNo = value;
    }

    /**
     * ???datas????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatas() {
        return datas;
    }

    /**
     * ????datas????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatas(String value) {
        this.datas = value;
    }

}
