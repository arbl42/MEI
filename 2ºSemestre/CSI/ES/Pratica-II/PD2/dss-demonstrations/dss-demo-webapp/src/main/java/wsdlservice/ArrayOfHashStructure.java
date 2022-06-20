
package wsdlservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHashStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHashStructure"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="HashStructure" type="{http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature}HashStructure" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHashStructure", namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", propOrder = {
    "hashStructure"
})
public class ArrayOfHashStructure {

    @XmlElement(name = "HashStructure", nillable = true)
    protected List<HashStructure> hashStructure;

    /**
     * Gets the value of the hashStructure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hashStructure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHashStructure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HashStructure }
     * 
     * 
     */
    public List<HashStructure> getHashStructure() {
        if (hashStructure == null) {
            hashStructure = new ArrayList<HashStructure>();
        }
        return this.hashStructure;
    }

}
