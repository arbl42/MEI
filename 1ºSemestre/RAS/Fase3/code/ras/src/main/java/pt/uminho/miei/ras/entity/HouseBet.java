package pt.uminho.miei.ras.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "house_bets")
public class HouseBet {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "bet_state", length = 64, nullable = false)
    private String betState;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "odd_win1", nullable = false)
    private Double oddWin1;

    @Column(name = "odd_draw")
    private Double oddDraw;

    @Column(name = "odd_win2", nullable = false)
    private Double oddWin2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBetState() {
        return betState;
    }

    public void setBetState(String betState) {
        this.betState = betState;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Double getOddWin1() {
        return oddWin1;
    }

    public void setOddWin1(Double oddWin1) {
        this.oddWin1 = oddWin1;
    }

    public Double getOddDraw() {
        return oddDraw;
    }

    public void setOddDraw(Double oddDraw) {
        this.oddDraw = oddDraw;
    }

    public Double getOddWin2() {
        return oddWin2;
    }

    public void setOddWin2(Double oddWin2) {
        this.oddWin2 = oddWin2;
    }
}
