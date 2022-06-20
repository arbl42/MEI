package eu.europa.esig.dss.web.controller;

import eu.europa.esig.dss.web.model.User;
import eu.europa.esig.dss.web.model.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

@Controller
@SessionAttributes(value = { "user"})
@RequestMapping(value = "/register")
public class RegisterController {
	@Autowired    
    UserDao dao;  

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	private static final String REGISTER_VIEW = "register";
	private static final String REGISTER_SUCCESS = "login";

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(Model model, HttpServletRequest request) {
		model.addAttribute("user", new User());  

		return REGISTER_VIEW;
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String validateForm(Model model, HttpServletRequest response,
			@ModelAttribute("user") @Valid User user, BindingResult result) {		
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				List<ObjectError> allErrors = result.getAllErrors();
				for (ObjectError error : allErrors) {
					LOG.debug(error.getDefaultMessage());
				}
			}
			return REGISTER_VIEW;
		}
		
		if(dao.save(user) == 0) // utilizador já existe
			// TODO avisar utilizador que username já existe
			return REGISTER_VIEW;

		return REGISTER_SUCCESS;
	}
}
