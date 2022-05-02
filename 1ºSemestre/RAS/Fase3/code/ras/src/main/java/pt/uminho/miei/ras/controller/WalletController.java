package pt.uminho.miei.ras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.uminho.miei.ras.controller.shared.CookieHandler;
import pt.uminho.miei.ras.dao.UserDAO;
import pt.uminho.miei.ras.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    public String index(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            Model model,
            HttpServletResponse response
    ) {

        if (userId.equals("")) {
            return "redirect:/user/create";
        }

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()) {
            CookieHandler.remove(response);
            return "redirect:/user/create";
        }

        model.addAttribute("logged", user);

        return "wallet/index";
    }

    @GetMapping("/add")
    public String add(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            Model model,
            HttpServletResponse response
    ) {

        if (userId.equals("")) {
            return "redirect:/user/create";
        }

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()) {
            CookieHandler.remove(response);
            return "redirect:/user/create";
        }

        model.addAttribute("logged", user);

        return "wallet/payment";
    }

    @GetMapping("/store")
    public String store(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            HttpServletResponse response,
            @RequestParam(name = "value") BigDecimal value
    ) {

        if (userId.equals("")) {
            return "redirect:/user/create";
        }

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()) {
            CookieHandler.remove(response);
            return "redirect:/user/create";
        }

        User u = user.get();
        u.setWallet(u.getWallet().add(value));
        userDAO.save(u);

        return "redirect:/wallet";
    }

    @GetMapping("/remove")
    public String remove(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            Model model,
            HttpServletResponse response
    ) {

        if (userId.equals("")) {
            return "redirect:/user/create";
        }

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()) {
            CookieHandler.remove(response);
            return "redirect:/user/create";
        }

        model.addAttribute("logged", user);

        return "wallet/withdrawal";
    }

    @GetMapping("/delete")
    public String delete(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "value") BigDecimal value
    ) {

        if (userId.equals("")) {
            return "redirect:/user/create";
        }

        Optional<User> user = userDAO.findById(userId);

        if (user.isEmpty()) {
            CookieHandler.remove(response);
            return "redirect:/user/create";
        }

        User u = user.get();

        if (u.getWallet().compareTo(value) < 0) {
            redirectAttributes.addFlashAttribute("message", "Saldo Insuficiente");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/wallet/remove";
        }

        u.setWallet(u.getWallet().subtract(value));
        userDAO.save(u);

        return "redirect:/wallet";
    }
}
