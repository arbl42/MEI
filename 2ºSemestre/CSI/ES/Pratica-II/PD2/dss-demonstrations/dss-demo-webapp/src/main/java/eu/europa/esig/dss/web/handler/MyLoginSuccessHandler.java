package eu.europa.esig.dss.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import eu.europa.esig.dss.web.model.UserDao;
import eu.europa.esig.dss.web.service.UserService;

public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    UserService userService;

    @Autowired    
    UserDao dao;

    @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
            
        // Obter o username do utilizador logado
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        // Definir o username do utilizador logado
        userService.setUsername(username);

        // Definir o numero de telemovel do utilizador
        String phoneNumber = dao.getUserPhoneNumber(username);
        if(phoneNumber != null)
            userService.setPhoneNumber(phoneNumber);
    }
}
