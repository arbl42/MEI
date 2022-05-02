package pt.uminho.miei.ras.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.miei.ras.entity.UserBet;

@Repository
public interface UserBetDAO extends CrudRepository<UserBet, String> {
    Iterable<UserBet> findAllByUserIdAndHouseBet_BetState(String userId, String betState);
    Iterable<UserBet> findAllByHouseBet_Id(String houseBetId);
}
