package eu.europa.esig.dss.web.controller;

import eu.europa.esig.dss.web.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes(value = { "user"})
@RequestMapping(value = "/login")
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	private static final String LOGIN_VIEW = "login";

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(Model model, HttpServletRequest request) {
		model.addAttribute("user", new User());
		return LOGIN_VIEW;
	}
}
