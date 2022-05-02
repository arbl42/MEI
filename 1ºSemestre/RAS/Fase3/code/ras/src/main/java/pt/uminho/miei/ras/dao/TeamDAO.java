package pt.uminho.miei.ras.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.miei.ras.entity.Team;

@Repository
public interface TeamDAO extends CrudRepository<Team, String> {
}
