
package mn.unitel.websvc.ucbs.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entr_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="prod_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultdesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractRequest", propOrder = {
    "contractType",
    "contractid",
    "entrNo",
    "id",
    "prodNo",
    "resultcode",
    "resultdesc",
    "rs"
})
public class ContractRequest {

    @XmlElementRef(name = "contractType", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> contractType;
    @XmlElementRef(name = "contractid", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> contractid;
    @XmlElementRef(name = "entr_no", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> entrNo;
    protected Integer id;
    @XmlElementRef(name = "prod_no", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> prodNo;
    @XmlElementRef(name = "resultcode", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resultcode;
    @XmlElementRef(name = "resultdesc", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resultdesc;
    @XmlElementRef(name = "rs", namespace = "http://ucbs.websvc.unitel.mn/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> rs;

    /**
     * Gets the value of the contractType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContractType(JAXBElement<String> value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the contractid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContractid() {
        return contractid;
    }

    /**
     * Sets the value of the contractid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContractid(JAXBElement<String> value) {
        this.contractid = value;
    }

    /**
     * Gets the value of the entrNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEntrNo() {
        return entrNo;
    }

    /**
     * Sets the value of the entrNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEntrNo(JAXBElement<String> value) {
        this.entrNo = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the prodNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProdNo() {
        return prodNo;
    }

    /**
     * Sets the value of the prodNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProdNo(JAXBElement<String> value) {
        this.prodNo = value;
    }

    /**
     * Gets the value of the resultcode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResultcode() {
        return resultcode;
    }

    /**
     * Sets the value of the resultcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResultcode(JAXBElement<String> value) {
        this.resultcode = value;
    }

    /**
     * Gets the value of the resultdesc property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResultdesc() {
        return resultdesc;
    }

    /**
     * Sets the value of the resultdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResultdesc(JAXBElement<String> value) {
        this.resultdesc = value;
    }

    /**
     * Gets the value of the rs property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRs() {
        return rs;
    }

    /**
     * Sets the value of the rs property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRs(JAXBElement<String> value) {
        this.rs = value;
    }

}
