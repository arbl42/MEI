package eu.europa.esig.dss.web.controller;

import eu.europa.esig.dss.web.model.User;
import eu.europa.esig.dss.web.model.UserDao;
import eu.europa.esig.dss.web.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;

@Controller
@SessionAttributes(value = {"user"})
@RequestMapping(value = "/user-area")
public class UserAreaController {
	@Autowired    
    UserDao dao;

	@Autowired
    UserService userService;

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	private static final String USER_AREA_VIEW = "user-area";
	private static final String HOME_VIEW = "home";

	@RequestMapping(method = RequestMethod.GET)
	public String showUserArea(Model model, HttpServletRequest request) {
		/* é preciso criar um user com todos os campos preenchidos
		pois caso contrário, ao submeter o form, dá erro porque
		o username e password não estão preenchidos */
		User u = User.getMockUser();
		
		u.setPhoneNumber(userService.getPhoneNumber());

		model.addAttribute("user", u);
		
		return USER_AREA_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String validateForm(Model model, HttpServletRequest response,
			@ModelAttribute("user") @Valid User user,
			BindingResult result) {		
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				List<ObjectError> allErrors = result.getAllErrors();
				for (ObjectError error : allErrors) {
					LOG.debug(error.getDefaultMessage());
				}
			}
			return USER_AREA_VIEW;
		}
		
		String username = userService.getUsername();
		String phoneNumber = user.getPhoneNumber();
		
		LOG.debug("Tentativa de guardar o numero " + phoneNumber + " do username " + username);
		if(username != null){
			if(dao.updatePhoneNumber(username, phoneNumber) == 0){ // erro a atualizar
				// TODO avisar utilizador que numero de telemovel nao foi guardado
				LOG.error("Erro a guardar numero de telemovel na base de dados");
			} else {
				/* guardar número de telemóvel atualizado apenas se também for guardado
				na base de dados para os dados estarem consistentes */
				userService.setPhoneNumber(phoneNumber);
				LOG.info("Sucesso a guardar o numero de telemovel!");
			}
		} else {
			LOG.error("Username nao guardado no servico...");
		}
		
		return HOME_VIEW;
	}
}
