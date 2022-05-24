
package mn.unitel.websvc.ucbs.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mn.unitel.websvc.ucbs.xsd package. 
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

    private final static QName _ContractRequestProdNo_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "prod_no");
    private final static QName _ContractRequestContractid_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "contractid");
    private final static QName _ContractRequestContractType_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "contractType");
    private final static QName _ContractRequestEntrNo_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "entr_no");
    private final static QName _ContractRequestResultdesc_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "resultdesc");
    private final static QName _ContractRequestResultcode_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "resultcode");
    private final static QName _ContractRequestRs_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "rs");
    private final static QName _LoginResultUsername_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "username");
    private final static QName _LoginResultPassword_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "password");
    private final static QName _LoginResultRole_QNAME = new QName("http://ucbs.websvc.unitel.mn/xsd", "role");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mn.unitel.websvc.ucbs.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoginResult }
     * 
     */
    public LoginResult createLoginResult() {
        return new LoginResult();
    }

    /**
     * Create an instance of {@link ContractRequest }
     * 
     */
    public ContractRequest createContractRequest() {
        return new ContractRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "prod_no", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestProdNo(String value) {
        return new JAXBElement<String>(_ContractRequestProdNo_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "contractid", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestContractid(String value) {
        return new JAXBElement<String>(_ContractRequestContractid_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "contractType", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestContractType(String value) {
        return new JAXBElement<String>(_ContractRequestContractType_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "entr_no", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestEntrNo(String value) {
        return new JAXBElement<String>(_ContractRequestEntrNo_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "resultdesc", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestResultdesc(String value) {
        return new JAXBElement<String>(_ContractRequestResultdesc_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "resultcode", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestResultcode(String value) {
        return new JAXBElement<String>(_ContractRequestResultcode_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "rs", scope = ContractRequest.class)
    public JAXBElement<String> createContractRequestRs(String value) {
        return new JAXBElement<String>(_ContractRequestRs_QNAME, String.class, ContractRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "username", scope = LoginResult.class)
    public JAXBElement<String> createLoginResultUsername(String value) {
        return new JAXBElement<String>(_LoginResultUsername_QNAME, String.class, LoginResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "password", scope = LoginResult.class)
    public JAXBElement<String> createLoginResultPassword(String value) {
        return new JAXBElement<String>(_LoginResultPassword_QNAME, String.class, LoginResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ucbs.websvc.unitel.mn/xsd", name = "role", scope = LoginResult.class)
    public JAXBElement<String> createLoginResultRole(String value) {
        return new JAXBElement<String>(_LoginResultRole_QNAME, String.class, LoginResult.class, value);
    }

}
