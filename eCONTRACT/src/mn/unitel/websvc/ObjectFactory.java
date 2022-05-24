
package mn.unitel.websvc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import mn.unitel.websvc.ucbs.xsd.ContractRequest;
import mn.unitel.websvc.ucbs.xsd.LoginResult;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mn.unitel.websvc package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OnlinecontractResponseReturn_QNAME = new QName("http://websvc.unitel.mn", "return");
    private final static QName _LoginPass_QNAME = new QName("http://websvc.unitel.mn", "pass");
    private final static QName _LoginAuth_QNAME = new QName("http://websvc.unitel.mn", "auth");
    private final static QName _LoginUser_QNAME = new QName("http://websvc.unitel.mn", "user");
    private final static QName _OnlinecontractNewContractType_QNAME = new QName("http://websvc.unitel.mn", "contractType");
    private final static QName _OnlinecontractNewProdNo_QNAME = new QName("http://websvc.unitel.mn", "prod_no");
    private final static QName _OnlinecontractContractid_QNAME = new QName("http://websvc.unitel.mn", "contractid");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mn.unitel.websvc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OnlinecontractNewResponse }
     * 
     */
    public OnlinecontractNewResponse createOnlinecontractNewResponse() {
        return new OnlinecontractNewResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link OnlinecontractResponse }
     * 
     */
    public OnlinecontractResponse createOnlinecontractResponse() {
        return new OnlinecontractResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link Onlinecontract }
     * 
     */
    public Onlinecontract createOnlinecontract() {
        return new Onlinecontract();
    }

    /**
     * Create an instance of {@link OnlinecontractNew }
     * 
     */
    public OnlinecontractNew createOnlinecontractNew() {
        return new OnlinecontractNew();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "return", scope = OnlinecontractResponse.class)
    public JAXBElement<ContractRequest> createOnlinecontractResponseReturn(ContractRequest value) {
        return new JAXBElement<ContractRequest>(_OnlinecontractResponseReturn_QNAME, ContractRequest.class, OnlinecontractResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "return", scope = OnlinecontractNewResponse.class)
    public JAXBElement<ContractRequest> createOnlinecontractNewResponseReturn(ContractRequest value) {
        return new JAXBElement<ContractRequest>(_OnlinecontractResponseReturn_QNAME, ContractRequest.class, OnlinecontractNewResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "return", scope = LoginResponse.class)
    public JAXBElement<LoginResult> createLoginResponseReturn(LoginResult value) {
        return new JAXBElement<LoginResult>(_OnlinecontractResponseReturn_QNAME, LoginResult.class, LoginResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "pass", scope = Login.class)
    public JAXBElement<String> createLoginPass(String value) {
        return new JAXBElement<String>(_LoginPass_QNAME, String.class, Login.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "auth", scope = Login.class)
    public JAXBElement<String> createLoginAuth(String value) {
        return new JAXBElement<String>(_LoginAuth_QNAME, String.class, Login.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "user", scope = Login.class)
    public JAXBElement<String> createLoginUser(String value) {
        return new JAXBElement<String>(_LoginUser_QNAME, String.class, Login.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "contractType", scope = OnlinecontractNew.class)
    public JAXBElement<String> createOnlinecontractNewContractType(String value) {
        return new JAXBElement<String>(_OnlinecontractNewContractType_QNAME, String.class, OnlinecontractNew.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "prod_no", scope = OnlinecontractNew.class)
    public JAXBElement<String> createOnlinecontractNewProdNo(String value) {
        return new JAXBElement<String>(_OnlinecontractNewProdNo_QNAME, String.class, OnlinecontractNew.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "auth", scope = OnlinecontractNew.class)
    public JAXBElement<String> createOnlinecontractNewAuth(String value) {
        return new JAXBElement<String>(_LoginAuth_QNAME, String.class, OnlinecontractNew.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "contractid", scope = Onlinecontract.class)
    public JAXBElement<String> createOnlinecontractContractid(String value) {
        return new JAXBElement<String>(_OnlinecontractContractid_QNAME, String.class, Onlinecontract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://websvc.unitel.mn", name = "auth", scope = Onlinecontract.class)
    public JAXBElement<String> createOnlinecontractAuth(String value) {
        return new JAXBElement<String>(_LoginAuth_QNAME, String.class, Onlinecontract.class, value);
    }

}
