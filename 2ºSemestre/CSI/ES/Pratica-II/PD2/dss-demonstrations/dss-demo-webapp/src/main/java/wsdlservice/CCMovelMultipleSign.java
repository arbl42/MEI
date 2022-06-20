
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
 *         &lt;element name="request" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}MultipleSignRequest" minOccurs="0"/&gt;
 *         &lt;element name="documents" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}ArrayOfHashStructure" minOccurs="0"/&gt;
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
    "request",
    "documents"
})
@XmlRootElement(name = "CCMovelMultipleSign")
public class CCMovelMultipleSign {

    @XmlElementRef(name = "request", namespace = "http://Ama.Authentication.Service/", type = JAXBElement.class, required = false)
    protected JAXBElement<MultipleSignRequest> request;
    @XmlElementRef(name = "documents", namespace = "http://Ama.Authentication.Service/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfHashStructure> documents;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link MultipleSignRequest }{@code >}
     *     
     */
    public JAXBElement<MultipleSignRequest> getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link MultipleSignRequest }{@code >}
     *     
     */
    public void setRequest(JAXBElement<MultipleSignRequest> value) {
        this.request = value;
    }

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfHashStructure }{@code >}
     *     
     */
    public JAXBElement<ArrayOfHashStructure> getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfHashStructure }{@code >}
     *     
     */
    public void setDocuments(JAXBElement<ArrayOfHashStructure> value) {
        this.documents = value;
    }

}
