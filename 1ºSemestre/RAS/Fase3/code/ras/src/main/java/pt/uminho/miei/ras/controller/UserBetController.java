package pt.uminho.miei.ras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.uminho.miei.ras.controller.shared.CookieHandler;
import pt.uminho.miei.ras.dao.HouseBetDAO;
import pt.uminho.miei.ras.dao.UserBetDAO;
import pt.uminho.miei.ras.dao.UserDAO;
import pt.uminho.miei.ras.entity.Game;
import pt.uminho.miei.ras.entity.HouseBet;
import pt.uminho.miei.ras.entity.User;
import pt.uminho.miei.ras.entity.UserBet;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/userBet")
public class UserBetController {

    @Autowired
    private UserBetDAO userBetDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HouseBetDAO houseBetDAO;

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

        Iterable<UserBet> openBets = sorted(userBetDAO.findAllByUserIdAndHouseBet_BetState(user.get().getId(), "Open").iterator());
        model.addAttribute("openBets", openBets);

        Iterable<UserBet> closedBets = sorted(userBetDAO.findAllByUserIdAndHouseBet_BetState(user.get().getId(), "Closed").iterator());
        model.addAttribute("closedBets", closedBets);

        return "userBet/index";
    }

    @GetMapping("/create")
    public String create(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            Model model,
            @RequestParam(name = "bet", required = false, defaultValue = "") String bet,
            @RequestParam(name = "houseBet") String houseBetId,
            UserBet userBet
            ) {
        Optional<User> user = userDAO.findById(userId);
        model.addAttribute("logged", user);

        Optional<HouseBet> houseBet = houseBetDAO.findById(houseBetId);

        if (houseBet.isEmpty()) {
            return "redirect:/houseBet";
        }

        model.addAttribute("houseBet", houseBet.get());
        model.addAttribute("bet", bet);

        return "userBet/create";
    }

    @PostMapping
    public String store(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            Model model,
            RedirectAttributes redirectAttributes,

            @Valid UserBet userBet,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Erro no registo da aposta");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/userBet/create?houseBet=" + userBet.getHouseBet().getId();
        }

        Optional<User> user = userDAO.findById(userId);
        model.addAttribute("logged", user);

        if (user.isEmpty()) {
            userBet.setToken(UUID.randomUUID().toString());
            userBetDAO.save(userBet);

            model.addAttribute("message", "Aposta registada!");
            model.addAttribute("alertClass", "alert-success");
            model.addAttribute("token", userBet.getToken());

            return "userBet/token";
        }

        User u = user.get();

        if (u.getWallet().compareTo(BigDecimal.valueOf(userBet.getPrice())) < 0) {
            redirectAttributes.addFlashAttribute("message", "Saldo Insuficiente");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/userBet/create?houseBet=" + userBet.getHouseBet().getId();
        }

        u.setWallet(u.getWallet().subtract(BigDecimal.valueOf(userBet.getPrice())));
        userDAO.save(u);

        userBet.setUser(u);
        userBetDAO.save(userBet);

        redirectAttributes.addFlashAttribute("message", "Aposta registada!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/userBet";
    }

    private List<UserBet> sorted(Iterator<UserBet> it) {
        List<UserBet> list = new ArrayList<>();
        while (it.hasNext()) {
            list.add(it.next());
        }

        return list.stream().sorted(Comparator.comparing(o -> o.getHouseBet().getGame().getGameDate())).collect(Collectors.toList());
    }
}
