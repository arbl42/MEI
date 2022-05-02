package pt.uminho.miei.ras.dao;

import org.springframework.data.repository.CrudRepository;
import pt.uminho.miei.ras.entity.Game;

public interface GameDAO extends CrudRepository<Game, String> {
}
