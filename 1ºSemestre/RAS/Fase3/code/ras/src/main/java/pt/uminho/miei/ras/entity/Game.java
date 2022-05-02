package pt.uminho.miei.ras.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sport", nullable = false)
    private Sport sport;

    @Column(name = "game_date", length = 64, nullable = false)
    private String gameDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "team1", nullable = false)
    private Team team1;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "team2", nullable = false)
    private Team team2;

    @Column(name = "game_result", length = 64)
    private String gameResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }
}
