
package wsdlservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetCertificateWithPinResult" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}SignStatus" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getCertificateWithPinResult"
})
@XmlRootElement(name = "GetCertificateWithPinResponse")
public class GetCertificateWithPinResponse {

    @XmlElementRef(name = "GetCertificateWithPinResult", namespace = "http://Ama.Authentication.Service/", type = JAXBElement.class, required = false)
    protected JAXBElement<SignStatus> getCertificateWithPinResult;

    /**
     * Gets the value of the getCertificateWithPinResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     *     
     */
    public JAXBElement<SignStatus> getGetCertificateWithPinResult() {
        return getCertificateWithPinResult;
    }

    /**
     * Sets the value of the getCertificateWithPinResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     *     
     */
    public void setGetCertificateWithPinResult(JAXBElement<SignStatus> value) {
        this.getCertificateWithPinResult = value;
    }

}
