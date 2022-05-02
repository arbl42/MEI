package pt.uminho.miei.ras.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_bets")
public class UserBet {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "token", length = 64, unique = true)
    private String token;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "bet", length = 64, nullable = false)
    private String bet;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "house_bet", nullable = false)
    private HouseBet houseBet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public HouseBet getHouseBet() {
        return houseBet;
    }

    public void setHouseBet(HouseBet houseBet) {
        this.houseBet = houseBet;
    }
}
