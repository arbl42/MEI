package eu.europa.esig.dss.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CMDOTPForm {
    @NotNull
    @Pattern(regexp = "[0-9]{6}", message = "{error.cmd.userOtp.wrongInput}")
    private String userOtp;

    public String getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }
}
