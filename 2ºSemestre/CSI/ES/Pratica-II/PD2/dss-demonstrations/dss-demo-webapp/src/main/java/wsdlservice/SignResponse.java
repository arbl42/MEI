
package wsdlservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ArrayOfHashStructure" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}ArrayOfHashStructure"/&gt;
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="Status" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}SignStatus"/&gt;
 *         &lt;element name="certificate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignResponse", namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", propOrder = {
    "arrayOfHashStructure",
    "signature",
    "status",
    "certificate"
})
public class SignResponse {

    @XmlElement(name = "ArrayOfHashStructure", required = true, nillable = true)
    protected ArrayOfHashStructure arrayOfHashStructure;
    @XmlElement(name = "Signature", required = true, nillable = true)
    protected byte[] signature;
    @XmlElement(name = "Status", required = true, nillable = true)
    protected SignStatus status;
    @XmlElement(required = true, nillable = true)
    protected String certificate;

    /**
     * Gets the value of the arrayOfHashStructure property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfHashStructure }
     *     
     */
    public ArrayOfHashStructure getArrayOfHashStructure() {
        return arrayOfHashStructure;
    }

    /**
     * Sets the value of the arrayOfHashStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfHashStructure }
     *     
     */
    public void setArrayOfHashStructure(ArrayOfHashStructure value) {
        this.arrayOfHashStructure = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignature(byte[] value) {
        this.signature = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link SignStatus }
     *     
     */
    public SignStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignStatus }
     *     
     */
    public void setStatus(SignStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the certificate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * Sets the value of the certificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificate(String value) {
        this.certificate = value;
    }

}
