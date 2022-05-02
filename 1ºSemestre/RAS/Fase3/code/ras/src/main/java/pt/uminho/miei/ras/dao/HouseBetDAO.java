package pt.uminho.miei.ras.dao;

import org.springframework.data.repository.CrudRepository;
import pt.uminho.miei.ras.entity.HouseBet;

public interface HouseBetDAO extends CrudRepository<HouseBet, String> {
    Iterable<HouseBet> findAllByBetState(String betState);
    Iterable<HouseBet> findAllByGame_Sport_NameAndBetState(String sportName, String betState);
}
