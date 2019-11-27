package shopper.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopper.Models.User;

@Repository("myUserRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
