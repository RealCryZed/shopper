package shopper.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shopper.Models.User;

@Repository("myUserRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User findAllByUsername(String username);
}
