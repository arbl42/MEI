package eu.europa.esig.dss.web.controller;

import eu.europa.esig.dss.enumerations.*;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.MimeType;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.spi.DSSUtils;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.web.WebAppUtils;
import eu.europa.esig.dss.web.editor.ASiCContainerTypePropertyEditor;
import eu.europa.esig.dss.web.editor.EnumPropertyEditor;
import eu.europa.esig.dss.web.model.CMDOTPForm;
import eu.europa.esig.dss.web.model.CMDSignatureDocumentForm;
import eu.europa.esig.dss.web.service.CMDService;
import eu.europa.esig.dss.web.service.SigningService;
import eu.europa.esig.dss.web.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes(value = { "signatureDocumentForm", "cmdOtpForm", "signedDocument" })
@RequestMapping(value = "/cmd-sign-a-document")
public class CMDSignatureController {

	private static final Logger LOG = LoggerFactory.getLogger(CMDSignatureController.class);

	private static final String SIGNATURE_START = "cmd-signature";
	private static final String SIGNATURE_GET_OTP = "cmd-signature-get-otp";
	private static final String SIGNATURE_SIGNED = "cmd-signature-signed";

	private static final String[] ALLOWED_FIELDS = { "documentToSign", "containerType", "signatureForm", "signaturePackaging",
			"signatureLevel", "digestAlgorithm", "signWithExpiredCertificate", "addContentTimestamp", "userId", "userPin", "userOtp" };
	
    @Value("${default.digest.algo}")
    private String defaultDigestAlgo;

	@Autowired
	private SigningService signingService;

	@Autowired
	private CMDService cmdService;

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(SignatureForm.class, new EnumPropertyEditor(SignatureForm.class));
		webDataBinder.registerCustomEditor(ASiCContainerType.class, new ASiCContainerTypePropertyEditor());
		webDataBinder.registerCustomEditor(SignaturePackaging.class, new EnumPropertyEditor(SignaturePackaging.class));
		webDataBinder.registerCustomEditor(SignatureLevel.class, new EnumPropertyEditor(SignatureLevel.class));
		webDataBinder.registerCustomEditor(DigestAlgorithm.class, new EnumPropertyEditor(DigestAlgorithm.class));
		webDataBinder.registerCustomEditor(EncryptionAlgorithm.class, new EnumPropertyEditor(EncryptionAlgorithm.class));
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder webDataBinder) {
		webDataBinder.setAllowedFields(ALLOWED_FIELDS);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showSignatureParameters(Model model, HttpServletRequest request) {
		CMDSignatureDocumentForm signatureDocumentForm = new CMDSignatureDocumentForm();

		signatureDocumentForm.setDigestAlgorithm(DigestAlgorithm.forName(defaultDigestAlgo, DigestAlgorithm.SHA256));
		signatureDocumentForm.setUserId(userService.getPhoneNumber());

		model.addAttribute("signatureDocumentForm", signatureDocumentForm);

		return SIGNATURE_START;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String requestSignature(Model model, HttpServletRequest response,
			@ModelAttribute("signatureDocumentForm") @Valid CMDSignatureDocumentForm signatureDocumentForm, BindingResult result) {
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				List<ObjectError> allErrors = result.getAllErrors();
				for (ObjectError error : allErrors) {
					LOG.debug(error.getDefaultMessage());
				}
			}
			return SIGNATURE_START;
		}

		// Get the user's certificates
		List<String> certificates;
		try {
			certificates = cmdService.getCertificatesOf(signatureDocumentForm.getUserId());
		} catch(SOAPFaultException | CertificateException | IOException e) {
			e.printStackTrace();
			result.addError(new ObjectError("userId", "UserId is not valid!"));
			return SIGNATURE_START;
		}

		// Set the user's certificates on the form object
		signatureDocumentForm.setBase64Certificate(certificates.get(0));
		signatureDocumentForm.setBase64CertificateChain(certificates.subList(1, certificates.size()));

		// Set the signing date and, if requested, the content timestamp
		signatureDocumentForm.setSigningDate(new Date());

		if (signatureDocumentForm.isAddContentTimestamp()) {
			signatureDocumentForm.setContentTimestamp(WebAppUtils.fromTimestampToken(signingService.getContentTimestamp(signatureDocumentForm)));
		}

		// Create a signing request
		String docName = signatureDocumentForm.getDocumentToSign().getOriginalFilename();
		ToBeSigned dataToSign = signingService.getDataToSign(signatureDocumentForm);
		if (dataToSign == null) {
			return null;
		}

		SignatureAlgorithm certificateSignatureAlgorithm = DSSUtils.loadCertificateFromBase64EncodedString(signatureDocumentForm.getBase64Certificate()).getSignatureAlgorithm();

		String processId = cmdService.sign(docName,
				dataToSign.getBytes(),
				certificateSignatureAlgorithm.getEncryptionAlgorithm(),
				signatureDocumentForm.getDigestAlgorithm(),
				signatureDocumentForm.getUserId(),
				signatureDocumentForm.getUserPin());

		signatureDocumentForm.setProcessId(processId);

		// Request OTP from user
		CMDOTPForm cmdOtpForm = new CMDOTPForm();
		model.addAttribute("cmdOtpForm", cmdOtpForm);

		model.addAttribute("signatureDocumentForm", signatureDocumentForm);
		model.addAttribute("digestAlgorithm", signatureDocumentForm.getDigestAlgorithm());

		return SIGNATURE_GET_OTP;
	}

	@RequestMapping(value = "/sign-document", method = RequestMethod.POST)
	public String signDocument(Model model, HttpServletRequest response,
							   @ModelAttribute("signatureDocumentForm") @Valid CMDSignatureDocumentForm signatureDocumentForm,
							   @ModelAttribute("cmdOtpForm") @Valid CMDOTPForm cmdOtpForm, BindingResult result) {
		String signature = cmdService.validateOtp(signatureDocumentForm.getProcessId(), cmdOtpForm.getUserOtp());

		if(signature == null) {
			return SIGNATURE_GET_OTP;
		}

		signatureDocumentForm.setBase64SignatureValue(signature);

		DSSDocument document = signingService.signDocument(signatureDocumentForm);
		InMemoryDocument signedDocument = new InMemoryDocument(DSSUtils.toByteArray(document), document.getName(), document.getMimeType());

		model.addAttribute("signedDocument", signedDocument);
		model.addAttribute("rootUrl", "sign-document");

		return SIGNATURE_SIGNED;
	}

	@RequestMapping(value = "/sign-document/download", method = RequestMethod.GET)
	public String downloadSignedFile(@ModelAttribute("signedDocument") InMemoryDocument signedDocument, HttpServletResponse response) {
		try {
			MimeType mimeType = signedDocument.getMimeType();
			if (mimeType != null) {
				response.setContentType(mimeType.getMimeTypeString());
			}
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + signedDocument.getName() + "\"");
			Utils.copy(new ByteArrayInputStream(signedDocument.getBytes()), response.getOutputStream());

		} catch (Exception e) {
			LOG.error("An error occurred while pushing file in response : " + e.getMessage(), e);
		}
		return null;
	}

	@ModelAttribute("asicContainerTypes")
	public ASiCContainerType[] getASiCContainerTypes() {
		return ASiCContainerType.values();
	}

	@ModelAttribute("signatureForms")
	public SignatureForm[] getSignatureForms() {
		return new SignatureForm[] { SignatureForm.XAdES, SignatureForm.CAdES, SignatureForm.PAdES, SignatureForm.JAdES};
	}

	@ModelAttribute("signaturePackagings")
	public SignaturePackaging[] getSignaturePackagings() {
		return SignaturePackaging.values();
	}

	@ModelAttribute("digestAlgos")
	public DigestAlgorithm[] getDigestAlgorithms() {
		DigestAlgorithm[] algos = new DigestAlgorithm[] { DigestAlgorithm.SHA1, DigestAlgorithm.SHA256, DigestAlgorithm.SHA384,
				DigestAlgorithm.SHA512 };
		return algos;
	}

	@ModelAttribute("isMockUsed")
	public boolean isMockUsed() {
		return signingService.isMockTSPSourceUsed();
	}

}
