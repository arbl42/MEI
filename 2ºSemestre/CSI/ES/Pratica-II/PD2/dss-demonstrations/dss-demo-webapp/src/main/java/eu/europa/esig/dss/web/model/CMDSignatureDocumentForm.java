package eu.europa.esig.dss.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CMDSignatureDocumentForm extends SignatureDocumentForm {

	@NotNull
	@Pattern(regexp = "(\\+351) *9[0-9]{8}", message = "{error.cmd.userId.wrongInput}")
	private String userId;

	@NotNull
	@Pattern(regexp = "[0-9]{4,8}", message = "{error.cmd.userPin.wrongInput}")
	private String userPin;

	private String processId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPin() {
		return userPin;
	}

	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

}
