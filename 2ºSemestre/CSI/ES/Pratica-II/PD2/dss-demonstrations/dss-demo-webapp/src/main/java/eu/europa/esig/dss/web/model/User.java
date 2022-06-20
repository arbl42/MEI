package eu.europa.esig.dss.web.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class User {
    private int id;

    @NotEmpty
	@Pattern(regexp = "^([a-zA-Z][a-zA-Z0-9]{4,20})$", message = "{error.user.username.wrongInput}")
    private String username;

    // 8 a 25 carateres, pelo menos uma letra e um numero
    @NotEmpty
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,25}$", message = "{error.user.password.wrongInput}")
    private String password;

    // +351 XXXXXXXXX
    @Pattern(regexp = "^\\+[0-9]{1,3} ?[0-9]{9}$", message = "{error.user.phoneNumber.wrongInput}")
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static User getMockUser(){
        User u = new User();
        u.username = "admin";
        u.password = "Password12345";
        u.phoneNumber = "+351 912345678";
        return u;
    }

}
