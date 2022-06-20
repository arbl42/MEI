package eu.europa.esig.dss.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import eu.europa.esig.dss.web.service.UserService;

public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Autowired
    UserService userService;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        // Remover o username e número de telemovel do utilizador logado
        userService.unsetUsername();
        userService.unsetPhoneNumber();
        
        super.onLogoutSuccess(request, response, authentication);

    }
}
