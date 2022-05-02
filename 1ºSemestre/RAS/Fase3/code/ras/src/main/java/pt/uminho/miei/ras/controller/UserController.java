package pt.uminho.miei.ras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.uminho.miei.ras.controller.shared.CookieHandler;
import pt.uminho.miei.ras.dao.UserDAO;
import pt.uminho.miei.ras.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/create")
    public String create(
            Model model,
            User user
    ) {

        Optional<User> empty = Optional.empty();
        model.addAttribute("logged", empty);

        return "login/create";
    }

    @PostMapping
    public String store(
            HttpServletResponse response,
            Model model,
            RedirectAttributes redirectAttributes,

            @Valid User user,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            Optional<User> empty = Optional.empty();
            model.addAttribute("logged", empty);
            return "login/create";
        }

        user.setWallet(BigDecimal.ZERO);
        user = userDAO.save(user);

        redirectAttributes.addFlashAttribute("message", "Utilizador criado com sucesso");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        CookieHandler.add(user.getId(), response);

        return "redirect:/user/edit";
    }

    @PostMapping("/login")
    public String login(
            HttpServletResponse response,
            Model model,
            RedirectAttributes redirectAttributes,
            @Valid User user
    ) {

        User u = userDAO.findFirstByEmailAndPassword(user.getEmail(), user.getPassword());

        if (u == null) {
            redirectAttributes.addFlashAttribute("message", "Credenciais Erradas!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/user/create";
        }

        CookieHandler.add(u.getId(), response);

        return "redirect:/user/edit";
    }

    @GetMapping("/edit")
    public String edit(
            @CookieValue("user") String userId,
            HttpServletResponse response,
            Model model
    ) {

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()) {
            CookieHandler.remove(response);
            return "redirect:/user/create";
        }

        model.addAttribute("user", user.get());
        model.addAttribute("logged", user);

        return "login/edit";
    }

    @PostMapping("/edit")
    public String update(
            @CookieValue("user") String userId,
            Model model,
            RedirectAttributes redirectAttributes,

            @Valid User user,
            BindingResult result
    ) {

        user.setId(userId);

        if (result.hasErrors()) {
            Optional<User> logged = userDAO.findById(userId);
            model.addAttribute("logged", logged);

            return "login/edit";
        }

        userDAO.save(user);

        redirectAttributes.addFlashAttribute("message", "Success");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/user/edit";
    }

    @GetMapping("/delete")
    public String delete(
            @CookieValue("user") String userId,
            HttpServletResponse response
    ) {

        Optional<User> user = userDAO.findById(userId);
        user.ifPresent(value -> userDAO.delete(value));

        CookieHandler.remove(response);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(
            HttpServletResponse response
    ) {
        CookieHandler.remove(response);

        return "redirect:/";
    }
}
