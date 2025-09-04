package finalmission.repository;

import finalmission.domain.Coach;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByAuthIdAndPassword(String s, String password);
}
