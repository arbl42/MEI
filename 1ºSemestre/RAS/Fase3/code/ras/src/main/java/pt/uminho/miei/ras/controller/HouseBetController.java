package pt.uminho.miei.ras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.uminho.miei.ras.dao.GameDAO;
import pt.uminho.miei.ras.dao.HouseBetDAO;
import pt.uminho.miei.ras.dao.UserBetDAO;
import pt.uminho.miei.ras.dao.UserDAO;
import pt.uminho.miei.ras.entity.Game;
import pt.uminho.miei.ras.entity.HouseBet;
import pt.uminho.miei.ras.entity.User;
import pt.uminho.miei.ras.entity.UserBet;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/houseBet")
public class HouseBetController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HouseBetDAO houseBetDAO;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private UserBetDAO userBetDAO;

    @GetMapping
    public String index(
            @CookieValue(value = "user", required = false, defaultValue = "") String userId,
            Model model,
            @RequestParam(name = "sport", required = false, defaultValue = "") String sport
    ) {
        Optional<User> user = userDAO.findById(userId);
        model.addAttribute("logged", user);

        Iterable<HouseBet> houseBets;

        if (sport.equals("")) {
            houseBets = sorted(houseBetDAO.findAllByBetState("Open").iterator());
        } else {
            houseBets = sorted(houseBetDAO.findAllByGame_Sport_NameAndBetState(sport, "Open").iterator());
        }

        model.addAttribute("sport", sport);
        model.addAttribute("houseBets", houseBets);

        return "houseBet/index";
    }

    @GetMapping("/close")
    @ResponseBody
    public String close(
            @RequestParam(name = "id", required = false, defaultValue = "") String id,
            @RequestParam(name = "result", required = false, defaultValue = "") String result
    ) {
        if (!result.equals("1") && !result.equals("X") && !result.equals("2")) {
            return "bad result type (1 X 2)";
        }

        Optional<HouseBet> h = houseBetDAO.findById(id);
        if (h.isEmpty()) {
            return "house bet id wrong";
        }

        HouseBet houseBet = h.get();

        if (houseBet.getBetState().equals("Closed")) {
            return "bet already closed";
        }

        houseBet.setBetState("Closed");

        Game game = houseBet.getGame();
        game.setGameResult(result);

        houseBetDAO.save(houseBet);
        gameDAO.save(game);

        double payAmount = 0;

        switch (result) {
            case "1":
                payAmount = houseBet.getOddWin1();
                break;
            case "X":
                payAmount = houseBet.getOddDraw();
                break;
            case "2":
                payAmount = houseBet.getOddWin2();
                break;
        }

        final double finalPayAmount = payAmount;

        Iterable<UserBet> userBets = userBetDAO.findAllByHouseBet_Id(houseBet.getId());
        userBets.forEach(userBet -> {
            if (!userBet.getBet().equals(result)) {
                return;
            }

            Optional<User> user = userDAO.findById(userBet.getUser().getId());

            if (user.isEmpty()) {
                return;
            }

            User u = user.get();
            double a = userBet.getPrice() * finalPayAmount;
            u.setWallet(u.getWallet().add(BigDecimal.valueOf(a)));
            userDAO.save(u);
        });

        return "done";
    }

    private List<HouseBet> sorted(Iterator<HouseBet> it) {
        List<HouseBet> list = new ArrayList<>();
        while (it.hasNext()) {
            list.add(it.next());
        }

        return list.stream().sorted(Comparator.comparing(o -> o.getGame().getGameDate())).collect(Collectors.toList());
    }
}

