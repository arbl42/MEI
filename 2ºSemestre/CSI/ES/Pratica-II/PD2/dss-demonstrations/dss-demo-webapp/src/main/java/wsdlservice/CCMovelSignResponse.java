
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
 *         &lt;element name="CCMovelSignResult" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}SignStatus" minOccurs="0"/&gt;
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
    "ccMovelSignResult"
})
@XmlRootElement(name = "CCMovelSignResponse")
public class CCMovelSignResponse {

    @XmlElementRef(name = "CCMovelSignResult", namespace = "http://Ama.Authentication.Service/", type = JAXBElement.class, required = false)
    protected JAXBElement<SignStatus> ccMovelSignResult;

    /**
     * Gets the value of the ccMovelSignResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     *     
     */
    public JAXBElement<SignStatus> getCCMovelSignResult() {
        return ccMovelSignResult;
    }

    /**
     * Sets the value of the ccMovelSignResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     *     
     */
    public void setCCMovelSignResult(JAXBElement<SignStatus> value) {
        this.ccMovelSignResult = value;
    }

}
