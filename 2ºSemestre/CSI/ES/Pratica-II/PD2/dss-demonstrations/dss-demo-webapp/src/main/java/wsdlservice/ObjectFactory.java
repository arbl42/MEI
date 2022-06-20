
package wsdlservice;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the wsdlservice package. 
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

    private final static QName _SignRequest_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "SignRequest");
    private final static QName _SignStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "SignStatus");
    private final static QName _SignResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "SignResponse");
    private final static QName _ArrayOfHashStructure_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "ArrayOfHashStructure");
    private final static QName _HashStructure_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "HashStructure");
    private final static QName _MultipleSignRequest_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "MultipleSignRequest");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _CCMovelSignRequest_QNAME = new QName("http://Ama.Authentication.Service/", "request");
    private final static QName _CCMovelSignResponseCCMovelSignResult_QNAME = new QName("http://Ama.Authentication.Service/", "CCMovelSignResult");
    private final static QName _GetCertificateApplicationId_QNAME = new QName("http://Ama.Authentication.Service/", "applicationId");
    private final static QName _GetCertificateUserId_QNAME = new QName("http://Ama.Authentication.Service/", "userId");
    private final static QName _GetCertificateResponseGetCertificateResult_QNAME = new QName("http://Ama.Authentication.Service/", "GetCertificateResult");
    private final static QName _ValidateOtpCode_QNAME = new QName("http://Ama.Authentication.Service/", "code");
    private final static QName _ValidateOtpProcessId_QNAME = new QName("http://Ama.Authentication.Service/", "processId");
    private final static QName _ValidateOtpResponseValidateOtpResult_QNAME = new QName("http://Ama.Authentication.Service/", "ValidateOtpResult");
    private final static QName _CCMovelMultipleSignDocuments_QNAME = new QName("http://Ama.Authentication.Service/", "documents");
    private final static QName _CCMovelMultipleSignResponseCCMovelMultipleSignResult_QNAME = new QName("http://Ama.Authentication.Service/", "CCMovelMultipleSignResult");
    private final static QName _ForceSMSCitizenId_QNAME = new QName("http://Ama.Authentication.Service/", "citizenId");
    private final static QName _ForceSMSResponseForceSMSResult_QNAME = new QName("http://Ama.Authentication.Service/", "ForceSMSResult");
    private final static QName _GetCertificateWithPinSignaturePin_QNAME = new QName("http://Ama.Authentication.Service/", "signaturePin");
    private final static QName _GetCertificateWithPinResponseGetCertificateWithPinResult_QNAME = new QName("http://Ama.Authentication.Service/", "GetCertificateWithPinResult");
    private final static QName _SignRequestDocName_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", "DocName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: wsdlservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CCMovelSign }
     * 
     */
    public CCMovelSign createCCMovelSign() {
        return new CCMovelSign();
    }

    /**
     * Create an instance of {@link SignRequest }
     * 
     */
    public SignRequest createSignRequest() {
        return new SignRequest();
    }

    /**
     * Create an instance of {@link CCMovelSignResponse }
     * 
     */
    public CCMovelSignResponse createCCMovelSignResponse() {
        return new CCMovelSignResponse();
    }

    /**
     * Create an instance of {@link SignStatus }
     * 
     */
    public SignStatus createSignStatus() {
        return new SignStatus();
    }

    /**
     * Create an instance of {@link GetCertificate }
     * 
     */
    public GetCertificate createGetCertificate() {
        return new GetCertificate();
    }

    /**
     * Create an instance of {@link GetCertificateResponse }
     * 
     */
    public GetCertificateResponse createGetCertificateResponse() {
        return new GetCertificateResponse();
    }

    /**
     * Create an instance of {@link ValidateOtp }
     * 
     */
    public ValidateOtp createValidateOtp() {
        return new ValidateOtp();
    }

    /**
     * Create an instance of {@link ValidateOtpResponse }
     * 
     */
    public ValidateOtpResponse createValidateOtpResponse() {
        return new ValidateOtpResponse();
    }

    /**
     * Create an instance of {@link SignResponse }
     * 
     */
    public SignResponse createSignResponse() {
        return new SignResponse();
    }

    /**
     * Create an instance of {@link CCMovelMultipleSign }
     * 
     */
    public CCMovelMultipleSign createCCMovelMultipleSign() {
        return new CCMovelMultipleSign();
    }

    /**
     * Create an instance of {@link MultipleSignRequest }
     * 
     */
    public MultipleSignRequest createMultipleSignRequest() {
        return new MultipleSignRequest();
    }

    /**
     * Create an instance of {@link ArrayOfHashStructure }
     * 
     */
    public ArrayOfHashStructure createArrayOfHashStructure() {
        return new ArrayOfHashStructure();
    }

    /**
     * Create an instance of {@link CCMovelMultipleSignResponse }
     * 
     */
    public CCMovelMultipleSignResponse createCCMovelMultipleSignResponse() {
        return new CCMovelMultipleSignResponse();
    }

    /**
     * Create an instance of {@link ForceSMS }
     * 
     */
    public ForceSMS createForceSMS() {
        return new ForceSMS();
    }

    /**
     * Create an instance of {@link ForceSMSResponse }
     * 
     */
    public ForceSMSResponse createForceSMSResponse() {
        return new ForceSMSResponse();
    }

    /**
     * Create an instance of {@link GetCertificateWithPin }
     * 
     */
    public GetCertificateWithPin createGetCertificateWithPin() {
        return new GetCertificateWithPin();
    }

    /**
     * Create an instance of {@link GetCertificateWithPinResponse }
     * 
     */
    public GetCertificateWithPinResponse createGetCertificateWithPinResponse() {
        return new GetCertificateWithPinResponse();
    }

    /**
     * Create an instance of {@link HashStructure }
     * 
     */
    public HashStructure createHashStructure() {
        return new HashStructure();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "SignRequest")
    public JAXBElement<SignRequest> createSignRequest(SignRequest value) {
        return new JAXBElement<SignRequest>(_SignRequest_QNAME, SignRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "SignStatus")
    public JAXBElement<SignStatus> createSignStatus(SignStatus value) {
        return new JAXBElement<SignStatus>(_SignStatus_QNAME, SignStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "SignResponse")
    public JAXBElement<SignResponse> createSignResponse(SignResponse value) {
        return new JAXBElement<SignResponse>(_SignResponse_QNAME, SignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfHashStructure }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfHashStructure }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "ArrayOfHashStructure")
    public JAXBElement<ArrayOfHashStructure> createArrayOfHashStructure(ArrayOfHashStructure value) {
        return new JAXBElement<ArrayOfHashStructure>(_ArrayOfHashStructure_QNAME, ArrayOfHashStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HashStructure }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HashStructure }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "HashStructure")
    public JAXBElement<HashStructure> createHashStructure(HashStructure value) {
        return new JAXBElement<HashStructure>(_HashStructure_QNAME, HashStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultipleSignRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MultipleSignRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "MultipleSignRequest")
    public JAXBElement<MultipleSignRequest> createMultipleSignRequest(MultipleSignRequest value) {
        return new JAXBElement<MultipleSignRequest>(_MultipleSignRequest_QNAME, MultipleSignRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "request", scope = CCMovelSign.class)
    public JAXBElement<SignRequest> createCCMovelSignRequest(SignRequest value) {
        return new JAXBElement<SignRequest>(_CCMovelSignRequest_QNAME, SignRequest.class, CCMovelSign.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "CCMovelSignResult", scope = CCMovelSignResponse.class)
    public JAXBElement<SignStatus> createCCMovelSignResponseCCMovelSignResult(SignStatus value) {
        return new JAXBElement<SignStatus>(_CCMovelSignResponseCCMovelSignResult_QNAME, SignStatus.class, CCMovelSignResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "applicationId", scope = GetCertificate.class)
    public JAXBElement<byte[]> createGetCertificateApplicationId(byte[] value) {
        return new JAXBElement<byte[]>(_GetCertificateApplicationId_QNAME, byte[].class, GetCertificate.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "userId", scope = GetCertificate.class)
    public JAXBElement<String> createGetCertificateUserId(String value) {
        return new JAXBElement<String>(_GetCertificateUserId_QNAME, String.class, GetCertificate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "GetCertificateResult", scope = GetCertificateResponse.class)
    public JAXBElement<String> createGetCertificateResponseGetCertificateResult(String value) {
        return new JAXBElement<String>(_GetCertificateResponseGetCertificateResult_QNAME, String.class, GetCertificateResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "code", scope = ValidateOtp.class)
    public JAXBElement<String> createValidateOtpCode(String value) {
        return new JAXBElement<String>(_ValidateOtpCode_QNAME, String.class, ValidateOtp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "processId", scope = ValidateOtp.class)
    public JAXBElement<String> createValidateOtpProcessId(String value) {
        return new JAXBElement<String>(_ValidateOtpProcessId_QNAME, String.class, ValidateOtp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "applicationId", scope = ValidateOtp.class)
    public JAXBElement<byte[]> createValidateOtpApplicationId(byte[] value) {
        return new JAXBElement<byte[]>(_GetCertificateApplicationId_QNAME, byte[].class, ValidateOtp.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "ValidateOtpResult", scope = ValidateOtpResponse.class)
    public JAXBElement<SignResponse> createValidateOtpResponseValidateOtpResult(SignResponse value) {
        return new JAXBElement<SignResponse>(_ValidateOtpResponseValidateOtpResult_QNAME, SignResponse.class, ValidateOtpResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultipleSignRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MultipleSignRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "request", scope = CCMovelMultipleSign.class)
    public JAXBElement<MultipleSignRequest> createCCMovelMultipleSignRequest(MultipleSignRequest value) {
        return new JAXBElement<MultipleSignRequest>(_CCMovelSignRequest_QNAME, MultipleSignRequest.class, CCMovelMultipleSign.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfHashStructure }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfHashStructure }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "documents", scope = CCMovelMultipleSign.class)
    public JAXBElement<ArrayOfHashStructure> createCCMovelMultipleSignDocuments(ArrayOfHashStructure value) {
        return new JAXBElement<ArrayOfHashStructure>(_CCMovelMultipleSignDocuments_QNAME, ArrayOfHashStructure.class, CCMovelMultipleSign.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "CCMovelMultipleSignResult", scope = CCMovelMultipleSignResponse.class)
    public JAXBElement<SignStatus> createCCMovelMultipleSignResponseCCMovelMultipleSignResult(SignStatus value) {
        return new JAXBElement<SignStatus>(_CCMovelMultipleSignResponseCCMovelMultipleSignResult_QNAME, SignStatus.class, CCMovelMultipleSignResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "processId", scope = ForceSMS.class)
    public JAXBElement<String> createForceSMSProcessId(String value) {
        return new JAXBElement<String>(_ValidateOtpProcessId_QNAME, String.class, ForceSMS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "citizenId", scope = ForceSMS.class)
    public JAXBElement<String> createForceSMSCitizenId(String value) {
        return new JAXBElement<String>(_ForceSMSCitizenId_QNAME, String.class, ForceSMS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "applicationId", scope = ForceSMS.class)
    public JAXBElement<byte[]> createForceSMSApplicationId(byte[] value) {
        return new JAXBElement<byte[]>(_GetCertificateApplicationId_QNAME, byte[].class, ForceSMS.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "ForceSMSResult", scope = ForceSMSResponse.class)
    public JAXBElement<SignStatus> createForceSMSResponseForceSMSResult(SignStatus value) {
        return new JAXBElement<SignStatus>(_ForceSMSResponseForceSMSResult_QNAME, SignStatus.class, ForceSMSResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "applicationId", scope = GetCertificateWithPin.class)
    public JAXBElement<byte[]> createGetCertificateWithPinApplicationId(byte[] value) {
        return new JAXBElement<byte[]>(_GetCertificateApplicationId_QNAME, byte[].class, GetCertificateWithPin.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "userId", scope = GetCertificateWithPin.class)
    public JAXBElement<String> createGetCertificateWithPinUserId(String value) {
        return new JAXBElement<String>(_GetCertificateUserId_QNAME, String.class, GetCertificateWithPin.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "signaturePin", scope = GetCertificateWithPin.class)
    public JAXBElement<String> createGetCertificateWithPinSignaturePin(String value) {
        return new JAXBElement<String>(_GetCertificateWithPinSignaturePin_QNAME, String.class, GetCertificateWithPin.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SignStatus }{@code >}
     */
    @XmlElementDecl(namespace = "http://Ama.Authentication.Service/", name = "GetCertificateWithPinResult", scope = GetCertificateWithPinResponse.class)
    public JAXBElement<SignStatus> createGetCertificateWithPinResponseGetCertificateWithPinResult(SignStatus value) {
        return new JAXBElement<SignStatus>(_GetCertificateWithPinResponseGetCertificateWithPinResult_QNAME, SignStatus.class, GetCertificateWithPinResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature", name = "DocName", scope = SignRequest.class)
    public JAXBElement<String> createSignRequestDocName(String value) {
        return new JAXBElement<String>(_SignRequestDocName_QNAME, String.class, SignRequest.class, value);
    }

}
