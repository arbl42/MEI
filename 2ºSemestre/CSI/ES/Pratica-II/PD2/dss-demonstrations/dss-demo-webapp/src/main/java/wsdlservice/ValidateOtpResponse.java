
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
 *         &lt;element name="ValidateOtpResult" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}SignResponse" minOccurs="0"/&gt;
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
    "validateOtpResult"
})
@XmlRootElement(name = "ValidateOtpResponse")
public class ValidateOtpResponse {

    @XmlElementRef(name = "ValidateOtpResult", namespace = "http://Ama.Authentication.Service/", type = JAXBElement.class, required = false)
    protected JAXBElement<SignResponse> validateOtpResult;

    /**
     * Gets the value of the validateOtpResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SignResponse }{@code >}
     *     
     */
    public JAXBElement<SignResponse> getValidateOtpResult() {
        return validateOtpResult;
    }

    /**
     * Sets the value of the validateOtpResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SignResponse }{@code >}
     *     
     */
    public void setValidateOtpResult(JAXBElement<SignResponse> value) {
        this.validateOtpResult = value;
    }

}
