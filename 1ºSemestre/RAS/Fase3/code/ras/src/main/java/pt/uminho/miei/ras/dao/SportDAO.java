package pt.uminho.miei.ras.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.miei.ras.entity.Sport;

@Repository
public interface SportDAO extends CrudRepository<Sport, String> {

}
