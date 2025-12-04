package ordering.repository;

import java.util.Optional;
import ordering.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}
