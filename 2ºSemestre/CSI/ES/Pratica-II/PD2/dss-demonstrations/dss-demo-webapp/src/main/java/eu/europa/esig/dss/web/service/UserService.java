package eu.europa.esig.dss.web.service;

import org.springframework.stereotype.Component;

@Component
public class UserService {
    private String username;
    private String phoneNumber;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void unsetUsername(){
        this.username = null;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void unsetPhoneNumber(){
        this.phoneNumber = null;
    }
}
