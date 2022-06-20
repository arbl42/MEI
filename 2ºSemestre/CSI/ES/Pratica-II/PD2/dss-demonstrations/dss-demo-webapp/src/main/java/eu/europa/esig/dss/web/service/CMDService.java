package eu.europa.esig.dss.web.service;

import com.google.common.primitives.Bytes;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.EncryptionAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import wsdlservice.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.*;

@Component
public class CMDService {
    private final static byte[] APPLICATION_ID = "b826359c-06f8-425e-8ec3-50a97a418916".getBytes();

    private static final Map<String, byte[]> RSA_PREFIXES = new HashMap<>();

    private final CCMovelDigitalSignature service;
    private final CCMovelSignature connector;

    static {
        RSA_PREFIXES.put("MD2", Base64.getDecoder().decode("MCAwDAYIKoZIhvcNAgIFAAQQ"));
        RSA_PREFIXES.put("MD5", Base64.getDecoder().decode("MCAwDAYIKoZIhvcNAgUFAAQQ"));
        RSA_PREFIXES.put("SHA1", Base64.getDecoder().decode("MCEwCQYFKw4DAhoFAAQU"));
        RSA_PREFIXES.put("SHA224", Base64.getDecoder().decode("MC0wDQYJYIZIAWUDBAIEBQAEHA=="));
        RSA_PREFIXES.put("SHA256", Base64.getDecoder().decode("MDEwDQYJYIZIAWUDBAIBBQAEIA=="));
        RSA_PREFIXES.put("SHA384", Base64.getDecoder().decode("MEEwDQYJYIZIAWUDBAICBQAEMA=="));
        RSA_PREFIXES.put("SHA512", Base64.getDecoder().decode("MFEwDQYJYIZIAWUDBAIDBQAEQA=="));
        RSA_PREFIXES.put("SHA512_224", Base64.getDecoder().decode("MC0wDQYJYIZIAWUDBAIFBQAEHA=="));
        RSA_PREFIXES.put("SHA512_256", Base64.getDecoder().decode("MDEwDQYJYIZIAWUDBAIGBQAEIA=="));
    }

    public CMDService() throws MalformedURLException {
        this.service = new CCMovelDigitalSignature(/*new URL("https://preprod.cmd.autenticacao.gov.pt/Ama.Authentication.Frontend/CCMovelDigitalSignature.svc?wsdl")*/);

        this.connector = service.getBasicHttpBindingCCMovelSignature();
    }

    public List<String> getCertificatesOf(String userId) throws CertificateException, IOException {
        final String certificates = connector.getCertificate(APPLICATION_ID, userId);

        BufferedInputStream contentPemFile = new BufferedInputStream(new ByteArrayInputStream(certificates.getBytes(StandardCharsets.UTF_8)));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        List<String> certChain = new ArrayList<>();
        while(contentPemFile.available() > 0) {
            Certificate certificate = cf.generateCertificate(contentPemFile);

            certChain.add(Base64.getEncoder().encodeToString(certificate.getEncoded()));
        }

        return certChain;
    }

    /**
     * This method computes the actual bytes to sign, depending on the
     *
     * @param doc
     * @param encryptionAlgorithm
     * @param digestAlgorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private byte[] hash(byte[] doc, EncryptionAlgorithm encryptionAlgorithm, DigestAlgorithm digestAlgorithm) throws NoSuchAlgorithmException, IOException {
        byte[] docDigest = digestAlgorithm.getMessageDigest().digest(doc);

        if(encryptionAlgorithm.getName().toUpperCase().contains("RSA")) {
//            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.getAlgorithm(encryptionAlgorithm, digestAlgorithm);
//
//            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
//            ASN1OutputStream out = ASN1OutputStream.create(bytesOut, ASN1Encoding.DER);
//
//            AlgorithmIdentifier signAlgOid = new AlgorithmIdentifier(new ASN1ObjectIdentifier(signatureAlgorithm.getOid()));
//            out.writeObject(signAlgOid);
//            out.writeObject(new DEROctetString(docDigest));
//
//            byte[] value = bytesOut.toByteArray();
//
//            return new DEROctetString(value).getEncoded();

            return Bytes.concat(RSA_PREFIXES.get(digestAlgorithm.getName()), docDigest);
        }

        return docDigest;
    }

    public String sign(String docName, byte[] doc, EncryptionAlgorithm encryptionAlgorithm, DigestAlgorithm digestAlgorithm, String userId, String userPin) {
        SignRequest request = new SignRequest();

        ObjectFactory factory = new ObjectFactory();

        request.setApplicationId(APPLICATION_ID);
        request.setDocName(factory.createSignRequestDocName(docName));

        try {
            request.setHash(this.hash(doc, encryptionAlgorithm, digestAlgorithm));
        } catch(NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }

        request.setUserId(userId);
        request.setPin(userPin);

        SignStatus status = connector.ccMovelSign(request);

        return status.getProcessId();
    }

    public String validateOtp(String processId, String userOtp) {
        SignResponse response = connector.validateOtp(userOtp, processId, APPLICATION_ID);

        byte[] signatureBytes = response.getSignature();

        if(signatureBytes == null) {
            return null;
        }

        return Base64.getEncoder().encodeToString(signatureBytes);
    }
}
