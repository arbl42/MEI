package pt.uminho.miei.ras.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.miei.ras.entity.User;

@Repository
public interface UserDAO extends CrudRepository<User, String> {
    User findFirstByEmailAndPassword(String email, String password);
    User findFirstByEmail(String email);
    User findFirstByUsername(String username);
    User findFirstByUsernameOrEmail(String username, String email);
}
